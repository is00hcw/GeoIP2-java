package com.maxmind.geoip2.webservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.Test;

import com.google.api.client.http.HttpTransport;
import com.maxmind.geoip2.exception.GeoIP2Exception;
import com.maxmind.geoip2.model.OmniResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Continent;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.RecordWithNames;
import com.maxmind.geoip2.record.RepresentedCountry;
import com.maxmind.geoip2.record.Subdivision;
import com.maxmind.geoip2.record.Traits;

public class NullTest {

    private final HttpTransport transport = new TestTransport();

    private final Client client = new Client(42, "abcdef123456", this.transport);

    @Test
    public void testDefaults() throws GeoIP2Exception, UnknownHostException {
        OmniResponse omni = this.client.omni(InetAddress.getByName("1.2.3.13"));

        City city = omni.getCity();
        assertNotNull(city);
        assertNull(city.getConfidence());

        Continent continent = omni.getContinent();
        assertNotNull(continent);
        assertNull(continent.getCode());

        Country country = omni.getCountry();
        assertNotNull(country);

        assertNotNull(omni.getLocation());

        assertNotNull(omni.getPostal());

        Country registeredCountry = omni.getRegisteredCountry();
        assertNotNull(registeredCountry);

        RepresentedCountry representedCountry = omni.getRepresentedCountry();
        assertNotNull(representedCountry);
        assertNull(representedCountry.getType());

        List<Subdivision> subdivisions = omni.getSubdivisionsList();
        assertNotNull(subdivisions);
        assertTrue(subdivisions.isEmpty());

        Subdivision subdiv = omni.getMostSpecificSubdivision();
        assertNotNull(subdiv);
        assertNull(subdiv.getIsoCode());
        assertNull(subdiv.getConfidence());

        Traits traits = omni.getTraits();
        assertNotNull(traits);
        assertNull(traits.getAutonomousSystemNumber());
        assertNull(traits.getAutonomousSystemOrganization());
        assertNull(traits.getDomain());
        assertNull(traits.getIpAddress());
        assertNull(traits.getIsp());
        assertNull(traits.getOrganization());
        assertNull(traits.getUserType());
        assertFalse(traits.isAnonymousProxy());
        assertFalse(traits.isSatelliteProvider());

        for (Country c : new Country[] { country, registeredCountry,
                representedCountry }) {
            assertNull(c.getConfidence());
            assertNull(c.getIsoCode());
        }

        for (RecordWithNames r : new RecordWithNames[] { city, continent,
                country, registeredCountry, representedCountry, subdiv }) {
            assertNull(r.getGeoNameId());
            assertNull(r.getName("en"));
            assertNull(r.getName("en", "pt"));
            assertTrue(r.getNames().isEmpty());
        }
    }
}
