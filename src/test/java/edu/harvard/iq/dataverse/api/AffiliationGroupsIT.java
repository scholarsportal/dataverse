package edu.harvard.iq.dataverse.api;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

public class AffiliationGroupsIT {

    @BeforeClass
    public static void setUpClass() {
        RestAssured.baseURI = UtilIT.getRestAssuredBaseUri();
    }

    @Test
    public void testAffiliationCrudOperations() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        String uniqueAlias1 = "affGroup" + UtilIT.getRandomIdentifier();
        objectBuilder.add("alias", uniqueAlias1)
                .add("name", "display name "+uniqueAlias1)
                .add("description", "description "+uniqueAlias1)
                .add("emaildomain", "unique email "+uniqueAlias1);
        arrayBuilder.add(objectBuilder);

        String uniqueAlias2 = "affGroup" + UtilIT.getRandomIdentifier();
        objectBuilder.add("alias", uniqueAlias2)
                .add("name", "display name "+uniqueAlias2)
                .add("description", "description "+uniqueAlias2)
                .add("emaildomain", "unique email "+uniqueAlias2);
        arrayBuilder.add(objectBuilder);

        // POST
        JsonObjectBuilder objectbuilder2 = Json.createObjectBuilder();
        objectbuilder2.add("affiliations", arrayBuilder);
        JsonObject jsonObject2 = objectbuilder2.build();
        Response response = UtilIT.createAffiliationGroup(jsonObject2);
        response.prettyPrint();
        response.then().assertThat().statusCode(OK.getStatusCode());

        // PUT
        JsonObjectBuilder updateBuilder = Json.createObjectBuilder()
                .add("alias", uniqueAlias2)
                .add("name", "display name "+uniqueAlias2)
                .add("description", "description "+uniqueAlias2)
                .add("emaildomain", "unique email "+uniqueAlias2);
        Response updateResponse = UtilIT.updateAffiliationGroup(uniqueAlias2+"modified", updateBuilder.build());
        updateResponse.prettyPrint();
        updateResponse.then().assertThat().statusCode(CREATED.getStatusCode());


    }
}
