package edu.harvard.iq.dataverse.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import edu.harvard.iq.dataverse.util.json.JsonUtil;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.OK;

public class AffiliationGroupsIT {

    @BeforeEach
    public static void setUpClass() {
        RestAssured.baseURI = UtilIT.getRestAssuredBaseUri();
    }

    @Test
    public void testAffiliationCrudOperations() {
        JsonArrayBuilder arrayBuilder = JsonUtil.createArrayBuilder();
        JsonObjectBuilder objectBuilder = JsonUtil.createObjectBuilder();

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
        JsonObjectBuilder objectbuilder2 = JsonUtil.createObjectBuilder();
        objectbuilder2.add("affiliations", arrayBuilder);
        JsonObject jsonObject2 = objectbuilder2.build();
        Response response = UtilIT.createAffiliationGroup(jsonObject2);
        response.prettyPrint();
        response.then().assertThat().statusCode(OK.getStatusCode());

        // PUT
        JsonObjectBuilder updateBuilder = JsonUtil.createObjectBuilder()
                .add("alias", uniqueAlias2)
                .add("name", "display name "+uniqueAlias2)
                .add("description", "description "+uniqueAlias2)
                .add("emaildomain", "unique email "+uniqueAlias2);
        Response updateResponse = UtilIT.updateAffiliationGroup(uniqueAlias2+"modified", updateBuilder.build());
        updateResponse.prettyPrint();
        updateResponse.then().assertThat().statusCode(CREATED.getStatusCode());


    }
}
