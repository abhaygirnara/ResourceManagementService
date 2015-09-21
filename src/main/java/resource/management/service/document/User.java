package resource.management.service.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongojack.ObjectId;

/**
 * This class is a Representation of the most basic parts of a Mongodb document.
 */
public class User {

    private String id;
    private String name;
    private int age;
    private String phone;

    @ObjectId
    @JsonProperty("_id")
    public String getId() {
        return this.id;
    }

    @ObjectId
    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
