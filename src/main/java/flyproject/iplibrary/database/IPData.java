package flyproject.iplibrary.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "ipdata")
public class IPData implements Serializable {

    @DatabaseField(id = true)
    private long id;
    @DatabaseField
    private String startip;

    @DatabaseField
    private String endip;

    @DatabaseField
    private long start_digital;

    @DatabaseField
    private long end_digital;

    @DatabaseField
    private String continent;

    @DatabaseField
    private String country;

    @DatabaseField
    private String province;

    @DatabaseField
    private String city;

    @DatabaseField
    private String area;

    @DatabaseField
    private String isp;

    @DatabaseField
    private long area_code;

    @DatabaseField
    private String english_name;

    @DatabaseField
    private String english_short_name;

    @DatabaseField
    private double longitude;

    @DatabaseField
    private double latitude;


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getArea_code() {
        return area_code;
    }

    public void setArea_code(long area_code) {
        this.area_code = area_code;
    }

    public long getEnd_digital() {
        return end_digital;
    }

    public void setEnd_digital(long end_digital) {
        this.end_digital = end_digital;
    }

    public long getStart_digital() {
        return start_digital;
    }

    public void setStart_digital(long start_digital) {
        this.start_digital = start_digital;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEndip() {
        return endip;
    }

    public void setEndip(String endip) {
        this.endip = endip;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public String getEnglish_short_name() {
        return english_short_name;
    }

    public void setEnglish_short_name(String english_short_name) {
        this.english_short_name = english_short_name;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStartip() {
        return startip;
    }

    public void setStartip(String startip) {
        this.startip = startip;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
