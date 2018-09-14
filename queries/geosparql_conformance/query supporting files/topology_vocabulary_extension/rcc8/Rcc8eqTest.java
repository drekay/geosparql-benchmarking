/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conformance_test.topology_vocabulary_extension.rcc8;

import conformance_test.topology_vocabulary_extension.PropertyTestMethods;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 *
 *
 * A.2.3.1 /conf/topology-vocab-extension/rcc8-spatial-relations
 *
 * Requirement: /req/topology-vocab-extension/rcc8-spatial-relations
 * Implementations shall allow the properties geo:rcc8eq, geo:rcc8dc,
 * geo:rcc8ec, geo:rcc8po, geo:rcc8tppi, geo:rcc8tpp, geo:rcc8ntpp,
 * geo:rcc8ntppi to be used in SPARQL graph patterns.
 *
 * a.) Test purpose: check conformance with this requirement
 *
 * b.) Test method: Verify that queries involving these properties return the
 * correct result for a test dataset
 *
 * c.) Reference: Clause 7.4 Req 6
 *
 * d.) Test Type: Capabilities
 */
public class Rcc8eqTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    /**
     * Equal returns t (TRUE) if two geometries of the same type have identical
     * X,Y coordinate values.
     */
    @Test
    public void rcc8eqBoundPostiveTest() {

        System.out.println("rcc8eq Bound Positive");
        String expResult = "http://example.org/Geometry#PolygonJ";
        String result = PropertyTestMethods.runBoundQuery("http://example.org/Geometry#PolygonJ", "geo:rcc8eq", "http://example.org/Geometry#PolygonJ");

        //System.out.println("Exp: " + expResult);
        //System.out.println("Res: " + result);
        assertEquals(expResult, result);
    }

    /**
     * Equal returns t (TRUE) if two geometries of the same type have identical
     * X,Y coordinate values.
     */
    @Test
    public void rcc8eqBoundNegativeTest() {

        System.out.println("rcc8eq Bound Negative");
        String expResult = null;
        String result = PropertyTestMethods.runBoundQuery("http://example.org/Geometry#PolygonJ", "geo:rcc8eq", "http://example.org/Geometry#PolygonL");

        //System.out.println("Exp: " + expResult);
        //System.out.println("Res: " + result);
        assertEquals(expResult, result);
    }

    /**
     * Equal returns t (TRUE) if two geometries of the same type have identical
     * X,Y coordinate values.
     */
    @Test
    public void rcc8eqUnboundPostiveTest() {

        System.out.println("rcc8eq Unbound Positive");
        List<String> expResult = new ArrayList<>();
        expResult.add("http://example.org/Feature#J");
        expResult.add("http://example.org/Geometry#PolygonJ");
        List<String> result = PropertyTestMethods.runUnboundQuery("http://example.org/Geometry#PolygonJ", "geo:rcc8eq");

        //System.out.println("Exp: " + expResult);
        //System.out.println("Res: " + result);
        assertEquals(expResult, result);
    }

    /**
     * Equal returns t (TRUE) if two geometries of the same type have identical
     * X,Y coordinate values.
     */
    @Test
    public void rcc8eqUnboundNegativeTest() {

        System.out.println("rcc8eq Unbound Negative");
        List<String> expResult = new ArrayList<>();
        expResult.add("http://example.org/Feature#J");
        expResult.add("http://example.org/Geometry#PolygonJ");
        List<String> result = PropertyTestMethods.runUnboundQuery("http://example.org/Geometry#PolygonJ", "geo:rcc8eq");

        //System.out.println("Exp: " + expResult);
        //System.out.println("Res: " + result);
        assertEquals(expResult, result);
    }

}
