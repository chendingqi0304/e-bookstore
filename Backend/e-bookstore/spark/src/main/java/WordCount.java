import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.sql.SparkSession;
import org.codehaus.janino.Java;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WordCount {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("c++", "java", "world","story","earth");//注意必须小写

        List<String> files = Arrays.asList(
                "file:/C:/Users/Alex/Desktop/e-bookstore/Backend/e-bookstore/text/CS.txt",
                "file:/C:/Users/Alex/Desktop/e-bookstore/Backend/e-bookstore/text/literature.txt",
                "file:/C:/Users/Alex/Desktop/e-bookstore/Backend/e-bookstore/text/Sci_fiction.txt"
        );

        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL Word Count")
                .config("spark.some.config.option", "some-value")
                .getOrCreate();

        JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());

        JavaRDD<String> allLines = sc.emptyRDD();
        for (String file : files) {
            JavaRDD<String> fileData = sc.textFile(file);
            allLines = allLines.union(fileData);
        }

        JavaRDD<String> filteredLines = allLines.filter(line -> {
            for (String word : words) {
                if (line.toLowerCase().contains(word)) {
                    return true;
                }
            }
            return false;
        });

        JavaRDD<String> wordsRDD = filteredLines.flatMap((FlatMapFunction<String, String>) line -> Arrays.asList(line.toLowerCase().split("\\s+")).iterator());
        JavaRDD<String> keywordsRDD = wordsRDD.filter(words::contains);

        // 使用 mapToPair 创建 (词, 1) 的键值对
        JavaPairRDD<String, Integer> wordPairs = keywordsRDD.mapToPair(word -> new Tuple2<>(word, 1));

        JavaPairRDD<String, Integer> wordCounts = wordPairs
                .groupByKey() // 按照词进行分组
                .mapValues(iterable -> {
                    int count = 0;
                    for (int value : iterable) {
                        count += value;
                    }
                    return count;
                });

        wordCounts.collect().forEach(tuple ->
                System.out.println("Word: " + tuple._1 + ", Count: " + tuple._2)
        );

        spark.stop();
    }
}