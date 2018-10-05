/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geosparql_benchmarking.geosparql_jena;

import execution.TestSystem;
import execution_results.QueryResult;
import io.github.galbiston.geosparql_jena.configuration.GeoSPARQLConfig;
import io.github.galbiston.geosparql_jena.implementation.index.IndexConfiguration.IndexOption;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeosparqlJena_TestSystem implements TestSystem {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private Dataset dataset;
    private IndexOption indexOption;
    private Boolean isUnionDefaultGraph;

    public GeosparqlJena_TestSystem(File datasetFolder, IndexOption indexOption, Boolean isUnionDefaultGraph) {
        //Access the datset from the folder.
        setup(TDBFactory.createDataset(datasetFolder.getAbsolutePath()), indexOption, isUnionDefaultGraph);
    }

    public GeosparqlJena_TestSystem(File datasetFolder, IndexOption indexOption) {
        //Access the datset from the folder.
        setup(TDBFactory.createDataset(datasetFolder.getAbsolutePath()), indexOption, false);
    }

    public GeosparqlJena_TestSystem(Dataset dataset, IndexOption indexOption, Boolean isUnionDefaultGraph) {

        //Copy the contents of the dataset to a new memory dataset.
        Dataset memDataset = DatasetFactory.createTxnMem();
        Iterator<String> graphNames = dataset.listNames();
        while (graphNames.hasNext()) {
            String graphName = graphNames.next();
            Model model = ModelFactory.createDefaultModel();
            model.add(dataset.getNamedModel(graphName));
            memDataset.addNamedModel(graphName, model);
        }

        setup(memDataset, indexOption, isUnionDefaultGraph);
    }

    private void setup(Dataset dataset, IndexOption indexOption, Boolean isUnionDefaultGraph) {
        this.dataset = dataset;
        this.indexOption = indexOption;
        this.isUnionDefaultGraph = isUnionDefaultGraph;
        try {
            GeoSPARQLConfig.setup(indexOption);
            GeoSPARQLConfig.reset();
        } catch (Exception ex) {
            throw new AssertionError("Issue accessing GeosparqlJena library. " + ex.getMessage());
        }
    }

    @Override
    public GeosparqlJena_QueryTask getQueryTask(String query) {
        return new GeosparqlJena_QueryTask(query, dataset, isUnionDefaultGraph);
    }

    @Override
    public QueryResult runUpdate(String query) {
        LOGGER.info("GeoSPARQL Jena Update: Started");
        long startNanoTime = System.nanoTime();
        UpdateRequest updateRequest = UpdateFactory.create(query);
        UpdateAction.execute(updateRequest, dataset);
        long resultsNanoTime = System.nanoTime();
        LOGGER.info("GeoSPARQL Jena Update: Completed");

        return new QueryResult(startNanoTime, resultsNanoTime);
    }

    @Override
    public void close() {
        dataset.close();
        if (TDBFactory.isBackedByTDB(dataset)) {
            TDBFactory.release(dataset);
        }
        dataset = null;
        GeoSPARQLConfig.reset();
        try {
            System.gc();
            Thread.sleep(5000); //Sleep for 5s to allow any Operating System clearing.
        } catch (InterruptedException ex) {
            LOGGER.error("Exception closing Jena: {}", ex.getMessage());
        }
        LOGGER.debug("GeosparqlJena closed");
    }

    @Override
    public String translateQuery(String query) {
        //No query translation required.
        return query;
    }

}
