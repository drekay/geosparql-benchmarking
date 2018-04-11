/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geosparql_benchmarking.experiments;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 */
public class DatasetLoadResult {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatasetLoadResult.class);

    private final String testSystemName;
    private final Boolean isCompleted;
    private final long startNanoTime;
    private final long endNanoTime;
    private final Duration startEndDuration;
    private final List<DatasetLoadTimeResult> datasetLoadTimeResults;
    private static Integer DATASET_COUNT = 0;
    private static final String[] SUMMARY_DEFAULT_HEADER = {"TestSystem", "Completed", "StartEndDuration"};
    private static String[] SUMMARY_HEADER = SUMMARY_DEFAULT_HEADER;

    public DatasetLoadResult(String testSystemName, Boolean isCompleted, long startNanoTime, long endNanoTime, List<DatasetLoadTimeResult> datasetLoadTimeResults) {
        this.testSystemName = testSystemName;
        this.isCompleted = isCompleted;
        this.startNanoTime = startNanoTime;
        this.endNanoTime = endNanoTime;
        this.startEndDuration = Duration.ofNanos(endNanoTime - startNanoTime);
        this.datasetLoadTimeResults = datasetLoadTimeResults;

        if (datasetLoadTimeResults.size() > DATASET_COUNT) {
            //Assuming consistency in Dataset namings.
            DATASET_COUNT = datasetLoadTimeResults.size();
            List<String> datasetNames = new ArrayList<>(DATASET_COUNT);
            for (DatasetLoadTimeResult result : datasetLoadTimeResults) {
                datasetNames.add(result.getDatasetName());
            }
            SUMMARY_HEADER = ArrayUtils.addAll(SUMMARY_DEFAULT_HEADER, datasetNames.toArray(new String[datasetNames.size()]));
        }
    }

    public String getTestSystemName() {
        return testSystemName;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public long getStartNanoTime() {
        return startNanoTime;
    }

    public long getEndNanoTime() {
        return endNanoTime;
    }

    public Duration getStartEndDuration() {
        return startEndDuration;
    }

    public List<DatasetLoadTimeResult> getDatasetLoadTimeResults() {
        return datasetLoadTimeResults;
    }

    @Override
    public String toString() {
        return "DatasetLoadResult{" + "testSystemName=" + testSystemName + ", isCompleted=" + isCompleted + ", startNanoTime=" + startNanoTime + ", endNanoTime=" + endNanoTime + ", startEndDuration=" + startEndDuration + ", datasetLoadTimeResults=" + datasetLoadTimeResults + '}';
    }

    public String[] writeSummary() {
        List<String> line = new ArrayList<>(SUMMARY_DEFAULT_HEADER.length + DATASET_COUNT);
        line.add(testSystemName);
        line.add(isCompleted.toString());
        line.add(startEndDuration.toString());

        for (DatasetLoadTimeResult result : datasetLoadTimeResults) {
            line.add(result.getStartEndDuration().toString());
        }
        return line.toArray(new String[line.size()]);
    }

    private static final DateTimeFormatter FILE_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    public static final void writeSummaryFile(File datasetLoadResultsFolder, List<DatasetLoadResult> datasetLoadResults) {

        String filename = "datasetload-" + LocalDateTime.now().format(FILE_DATE_TIME_FORMAT) + ".csv";
        File summaryFile = new File(datasetLoadResultsFolder, filename);
        try (CSVWriter writer = new CSVWriter(new FileWriter(summaryFile))) {
            writer.writeNext(SUMMARY_HEADER);
            for (DatasetLoadResult datasetLoadResult : datasetLoadResults) {
                writer.writeNext(datasetLoadResult.writeSummary());
            }

        } catch (IOException ex) {
            LOGGER.error("IOException: {}", ex.getMessage());
        }

    }

}
