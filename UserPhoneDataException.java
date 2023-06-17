public class UserPhoneDataException extends Exception{
    private String invalidData;
    private String detailInfo;
    public UserPhoneDataException(String message) {
        super(message);
    }

    public UserPhoneDataException() {
        this("Косяк в номере телефона");
    }

    public void setInvalidData(String invalidData) {
        this.invalidData = invalidData;
    }

    public String getInvalidData() {
        return invalidData;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public String getDetailInfo() {
        return detailInfo;
    }
}
