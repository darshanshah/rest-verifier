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

    public static boolean sumOfGenIdMaxSeven(Reviews reviews) {
        boolean result = true;
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

        if (count > 7) {
            result = false;
        }
        return result;
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
