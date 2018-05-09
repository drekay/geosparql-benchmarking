/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geosparql_benchmarking.geosparql;

import geosparql_benchmarking.experiments.QueryCase;
import geosparql_benchmarking.experiments.QueryLoader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class GeometryExtensionProperties {

    public static final String BASE_FOLDER = "../Core/geosparql_benchmarking/geometry_extension_properties";

    public static List<QueryCase> loadQueries() {
        List<QueryCase> queryCases = new ArrayList<>();
        queryCases.add(new QueryCase("GEP1", "GeometryExtensionProperties", QueryLoader.readFile(BASE_FOLDER + "/CoordinateDimension.spl")));
        queryCases.add(new QueryCase("GEP2", "GeometryExtensionProperties", QueryLoader.readFile(BASE_FOLDER + "/Dimension.spl")));
        queryCases.add(new QueryCase("GEP3", "GeometryExtensionProperties", QueryLoader.readFile(BASE_FOLDER + "/IsEmpty.spl")));
        queryCases.add(new QueryCase("GEP4", "GeometryExtensionProperties", QueryLoader.readFile(BASE_FOLDER + "/IsSimple.spl")));
        queryCases.add(new QueryCase("GEP5", "GeometryExtensionProperties", QueryLoader.readFile(BASE_FOLDER + "/SpatialDimension.spl")));
        return queryCases;
    }

}
