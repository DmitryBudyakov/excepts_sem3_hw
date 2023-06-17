public class UserSexDataException extends Exception{
    private String invalidData;
    private String detailInfo;
    public UserSexDataException(String message) {
        super(message);
    }

    public UserSexDataException() {
        this("Косяк в указании пола.");
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
