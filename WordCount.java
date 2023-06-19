


package org.apache.beam.examples;


import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.metrics.Counter;
import org.apache.beam.sdk.metrics.Distribution;
import org.apache.beam.sdk.metrics.Metrics;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.Validation.Required;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
//import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import  org.apache.beam.examples.JsonUrlReader2;
import  org.apache.beam.examples.Sismos;


public class WordCount {
    
   
  static class ExtractWordsFn extends DoFn<String, String> {
    private final Counter emptyLines = Metrics.counter(ExtractWordsFn.class, "emptyLines");
    private final Distribution lineLenDist =
        Metrics.distribution(ExtractWordsFn.class, "lineLenDistro");
   
    
    @DoFn.ProcessElement
    public void processElement(@DoFn.Element String element, DoFn.OutputReceiver<String> receiver) throws DatabindException, MalformedURLException, IOException {
    
 
        System.out.println("Invocando a JsonUrlReader");	    
        ArrayList<Sismos> aSismos;

        JsonUrlReader2 j = new JsonUrlReader2();

        aSismos = j.cargarURL();

        System.out.println("Finalizando carga URL JsonUrlReader");	    
        
      
        for (Sismos sismos : aSismos) {

            //String word = sim.toString();
            String word = sismos.toCSV();
            receiver.output(word);
        }
    }
  }

  
  public static class FormatAsTextFn extends SimpleFunction<KV<String, Long>, String> {
    @Override
    public String apply(KV<String, Long> input) {
      //return input.getKey() + ": " + input.getValue();
      return input.getKey();  
    }
  }
  
  public static class CountWords
      extends PTransform<PCollection<String>, PCollection<KV<String, Long>>> {
    @Override
    public PCollection<KV<String, Long>> expand(PCollection<String> lines) {

      // Convert lines of text into individual words.
      PCollection<String> words = lines.apply(ParDo.of(new ExtractWordsFn()));

      // Count the number of times each word occurs.
      PCollection<KV<String, Long>> wordCounts = words.apply(Count.perElement());

      return wordCounts;
    }
  }
  
  public interface WordCountOptions extends PipelineOptions {

  
    @Description("Path of the file to read from")
    @Default.String("gs://dataflow-apache-quickstart_dataflowbatchjson/entrada/valentina.txt")
    String getInputFile();

    void setInputFile(String value);

  
    @Description("Path of the file to write to")
    @Required
    String getOutput();

    void setOutput(String value);
  }
  
  static void runWordCount(WordCountOptions options) {
    Pipeline p = Pipeline.create(options);

    p.apply("ReadLines", TextIO.read().from(options.getInputFile()))
        .apply(new CountWords())
        .apply(MapElements.via(new FormatAsTextFn()))
        .apply("WriteCounts", TextIO.write().to(options.getOutput()));

    p.run().waitUntilFinish();
  }

  public static void main(String[] args) {
    WordCountOptions options =
        PipelineOptionsFactory.fromArgs(args).withValidation().as(WordCountOptions.class);

    runWordCount(options);
  }
}
