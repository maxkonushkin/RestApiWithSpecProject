package models.review;

public record PutReviewRequestModel(int club, String review,
                                    int assessment, int readPages){
}
