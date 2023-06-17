public class UserFullNameDataException extends Exception{
    private String invalidData;
    private String detailInfo;

    public UserFullNameDataException(String message) {
        super(message);
    }

    public UserFullNameDataException() {
        this("Косяк в ФИО");
    }

    public String getInvalidData() {
        return invalidData;
    }

    public void setInvalidData(String invalidData) {
        this.invalidData = invalidData;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public String getDetailInfo() {
        return detailInfo;
    }
}
