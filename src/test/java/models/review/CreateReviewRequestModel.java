package models.review;

public record CreateReviewRequestModel(int club, String review,
                                       int assessment, int readPages) {

}
