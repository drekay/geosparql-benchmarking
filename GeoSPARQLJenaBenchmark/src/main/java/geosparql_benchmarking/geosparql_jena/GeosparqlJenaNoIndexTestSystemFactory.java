/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geosparql_benchmarking.geosparql_jena;

import execution.TestSystem;
import implementation.index.IndexOption;
import java.lang.invoke.MethodHandles;
import org.apache.jena.query.Dataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gerg
 */
public class GeosparqlJenaNoIndexTestSystemFactory extends GeosparqlJenaInMemoryTestSystemFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String TEST_SYSTEM_NAME = "GeoSparqlJenaNoIndex";

    public GeosparqlJenaNoIndexTestSystemFactory(Dataset dataset, String resultsFolder, Boolean inferenceEnabled) {
        super(dataset, resultsFolder, inferenceEnabled);
    }

    @Override
    public TestSystem getTestSystem() {
        return new GeosparqlJenaTestSystem(dataset, IndexOption.NONE);
    }

    @Override
    public String getTestSystemName() {
        return TEST_SYSTEM_NAME;
    }

}
