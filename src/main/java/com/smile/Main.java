package com.smile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.commons.csv.CSVFormat;
import com.thoughtworks.xstream.XStream;
import smile.classification.LogisticRegression;
import smile.io.Read;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        String[] HEADERS = {"id", "reason.for.absence", "month.of.absence", "day.of.the.week", "seasons", "transportation.exp", "residence.dist", "service time", "age", "work.load", "hit.target", "discipline", "education", "son", "social.drinking", "social.smoking", "pet", "weight", "height", "bmi", "absent.hours", "absent"};
        CSVFormat format = CSVFormat.DEFAULT.builder().setHeader(HEADERS).setSkipHeaderRecord(true).build();



        var raw_data = Read.csv("absent.csv", format);
        raw_data = raw_data.drop("id").drop("absent.hours");
        var model = LogisticRegression.Binomial.binomial(raw_data.drop("absent").toArray(), raw_data.column("absent").toIntArray());
        
        var xstream = new XStream();

        var xml = xstream.toXML(model);

        BufferedWriter writer = new BufferedWriter(new FileWriter("model.xml"));
        writer.write(xml);
        writer.close();

    }
}