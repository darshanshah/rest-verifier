import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Util {

    public static boolean checkTitleHasPalindrome(Reviews reviews) {
        int count = 0;
        boolean result = true;
        for (int i = 0; i < reviews.getReviews().size(); i++) {
            Review review = reviews.getReviews().get(i);
            String[] listOFWord = review.getTitle().split(" ");
            for (int j = 0; j < listOFWord.length; j++) {
                if (isPalindrome(listOFWord[j])) {
                    count++;
                    break;
                }
            }
        }
        if (count < 1) {
            result = false;
        }
        return result;
    }

    public static boolean isPalindrome(String word) {
        for (int i = 0, j = word.length() - 1; i < j; i++, j--) {
            if (word.charAt(i) != word.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    public static int sumOfGenId(Reviews reviews) {
        int count = 0;
        for (int i = 0; i < reviews.getReviews().size(); i++) {
            Review review = reviews.getReviews().get(i);
            List<Integer> listOfGenId = review.getGenre_ids();
            int total = 0;
            for (int j = 0; j < listOfGenId.size(); j++) {
                total += listOfGenId.get(j);
                if (total > 400) {
                    count++;
                    break;
                }
            }
        }

        return count;
    }

    public static boolean checkSortingRequirement(Reviews reviews) {
        int count = getCountForSortingRequirement(reviews);
        if (reviews.getReviews().size() == count) {
            return true;
        } else {
            System.out.println("Number of count is failed " + (reviews.getReviews().size() - count));
            return false;
        }
    }

    public static int getCountForSortingRequirement(Reviews reviews) {
        boolean nullCheck = false;
        int count = 0;
        for (int i = 0; i < reviews.getReviews().size(); i++) {
            Review review = reviews.getReviews().get(i);
            if (review.genre_ids.size() > 0) {
                nullCheck = true;
            }
            if (nullCheck) {
                if (review.getGenre_ids().size() == 0) continue;
                if (checkAscendingGenreId(review)) {
                    count++;
                }
            } else {
                if (i >= 1) {
                    if (reviews.getReviews().get(i - 1).getId() > reviews.getReviews().get(i).getId()) {
                        continue;
                    } else {
                        count++;
                    }
                }
            }

        }
        return count;
    }


    public static boolean checkAscendingGenreId(Review review) {
        boolean result = true;
        for (int i = 1; i < review.getGenre_ids().size(); i++) {
            if (review.getGenre_ids().get(i - 1) > review.getGenre_ids().get(i)) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static int checkTitleContainTitle(Reviews reviews) {
        ArrayList<Review> listOfReview = new ArrayList<>(reviews.getReviews().size() + 1);

        for (Review review : reviews.getReviews()) {
            listOfReview.add(review);
        }
        Reviews rev = new Reviews();
        rev.setReviews(listOfReview);
        rev.sortByTitleLength();
        int counter = 0;
        for (int i = 0; i < rev.getReviews().size(); i++) {
            for (int j = i + 1; j < rev.getReviews().size(); j++) {
                if (rev.getReviews().get(j).getTitle().contains(rev.getReviews().get(i).getTitle())) {
                    counter++;
                    continue;
                }
            }
        }

        return counter;
    }

    public static boolean noMoviesShouldHaveSameImage(Reviews reviews) {
        HashSet<String> uniqueImagePath = new HashSet<>();
        Boolean isContainUniquePath = true;
        for (int i = 0; i < reviews.getReviews().size(); i++) {
            Review review = reviews.getReviews().get(i);
            if (review.getPoster_path() != null) {
                if (uniqueImagePath.contains(review.getPoster_path())) {
                    isContainUniquePath = false;
                    break;
                } else {
                    uniqueImagePath.add(review.getPoster_path());
                }
            }
        }
        return isContainUniquePath;
    }

}
