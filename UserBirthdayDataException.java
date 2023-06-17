public class UserBirthdayDataException extends Exception{
    private String invalidData;
    private String detailInfo;
    public UserBirthdayDataException(String message) {
        super(message);
    }

    public UserBirthdayDataException() {
        this("Косяк в формате даты рождения");
    }

    public String getInvalidData() {
        return invalidData;
    }

    public void setInvalidData(String invalidData) {
        this.invalidData = invalidData;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }
}
