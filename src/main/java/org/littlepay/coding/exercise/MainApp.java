package org.littlepay.coding.exercise;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


import static java.util.stream.Collectors.groupingBy;

import jdk.nashorn.internal.runtime.ECMAException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 */
public class MainApp {

    private static Logger logger = LogManager.getLogger();

    public static void tappingtoTripsProcessor(String inputFilepath , String outputFilepath) {
        logger.info("Executing tapping To Trips processor");

        try {
            List<Tap> rawTappinngData = readTapsCSV(inputFilepath);
            Map<String, List<Tap>> sortedTappingByCardNo = rawTappinngData.stream().collect(groupingBy(Tap::getPan));
            logger.info("Total raw tapping data records read from csv are "+rawTappinngData.size()+ ", Total unique cards are "+ sortedTappingByCardNo.size());

            List<Trip> trips = new ArrayList<>();
            for(String cardNo : sortedTappingByCardNo.keySet())   trips.addAll(listTripsForTaps(sortedTappingByCardNo.get(cardNo)));

            logger.info("Total total Trips generated for the csv are "+trips.size());


            writeObjectWithHeadertoCSV(trips,outputFilepath);
        }catch (Exception e){
            logger.error(" Unable to Run process"+ e.getMessage()+" \n " + e);
        }



    }



    public static List<Trip> listTripsForTaps(List<Tap> taps ){
        List<Trip> trips = new ArrayList<>();
        Tap prevtap = new Tap();

        // sorting card tapping data based on time
        taps.sort(Comparator.comparing(Tap::getDateTimeUTC));

        for (Tap tap : taps){
            Trip trip = new Trip();
            trip.setPan(tap.getPan());
            trip.setBusID(tap.getBusID());
            trip.setCompanyId(tap.getCompanyId());
            trip.setStarted(prevtap.getDateTimeUTC());
            trip.setFromStopId(prevtap.getStopId());

            if (prevtap.getTapType() != null && !( prevtap.getTapType().equalsIgnoreCase("off") && tap.getTapType().equalsIgnoreCase("on"))) {
                    trip.setFinished( (tap.getTapType().equalsIgnoreCase("off")) ? tap.getDateTimeUTC() : null);
                    trip.setToStopId( (tap.getTapType().equalsIgnoreCase("off")) ? tap.getStopId() : "unknown");
                    trips.add(trip);
                logger.debug(
                        trip.getStarted()+" , "+trip.getFinished()+" , "+trip.getDurationSecs()+" , "+trip.getFromStopId()+" , "+trip.getToStopId()+" , "+
                                trip.getChargeAmount()+" , "+trip.getCompanyId()+" , "+trip.getBusID()+" , "+trip.getPan()+" , "+trip.getStatus());
            }else{
                if((taps.indexOf(tap) + 1) == taps.size()){
                    trip.setStarted(tap.getDateTimeUTC());
                    trip.setFromStopId(tap.getStopId());
                    trip.setFinished(null);
                    trip.setToStopId("unknown");
                    trips.add(trip);
                    logger.debug(
                            trip.getStarted()+" , "+trip.getFinished()+" , "+trip.getDurationSecs()+" , "+trip.getFromStopId()+" , "+trip.getToStopId()+" , "+
                                    trip.getChargeAmount()+" , "+trip.getCompanyId()+" , "+trip.getBusID()+" , "+trip.getPan()+" , "+trip.getStatus());
                }
            }
            prevtap = tap;
        }

        return trips;
    }

    /** this is a writerTis is to read CSV File
     **
     **/
    public static List<Tap> readTapsCSV(String filePath)  throws Exception{
        ClassLoader classLoader = MainApp.class.getClassLoader();         ;
        File readfile = new File((filePath != null && !filePath.isEmpty() && !filePath.isEmpty()) ? filePath+"taps.csv" : classLoader.getResource("taps.csv").getFile());
        logger.debug("The path for reading the trips csv is "+classLoader.getResource("taps.csv").getPath());

        BeanListProcessor<Tap> rowProcessor = new BeanListProcessor<Tap>(Tap.class);

        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);

        CsvParser parser = new CsvParser(parserSettings);
        parser.parse(readfile);

        List<Tap> tapsbeans = rowProcessor.getBeans();

       return tapsbeans;
    }

    /** this is a writer
     **
     **/
    public static String writeObjectWithHeadertoCSV(List<Trip> tripObjtocsv , String path ){
         CsvWriterSettings settings = new CsvWriterSettings();
         // Any null values will be written as ?
         settings.setNullValue(" ");
         // Creates a BeanWriterProcessor that handles annotated fields in the TestBean class.
         settings.setRowWriterProcessor(new BeanWriterProcessor<Trip>(Trip.class));
         try {
             path = path != null && !path.isEmpty() ? path+"trips.csv": "trips.csv";
             FileOutputStream fileout = new FileOutputStream(path);
             Writer outputWriter=new OutputStreamWriter(fileout ,"UTF-8");
             // Creates a writer with the above settings;
             CsvWriter writer = new CsvWriter(outputWriter, settings);
             // Writes the headers specified in the settings
             writer.writeHeaders();
             tripObjtocsv.forEach(triptocsv -> writer.processRecord(triptocsv));
             writer.close();
             logger.info("csv file sucessfully create  " + path);
         }catch (Exception e){
             logger.error(" Unable to Write Trips to csv "+ e.getMessage()+" \n " + e);
         }
         return path;
    }

}

