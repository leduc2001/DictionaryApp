import java.util.Scanner;

public class DictionaryCommandline {
    DictionaryManagement DM = new DictionaryManagement();

    /**
     * Hiển thị danh sách từ điển
     * @param dictionary từ điển
     */

    public void showAllWord(Dictionary dictionary) {
        System.out.println("No\t\t| English\t\t| Vietnamese");
        int i=0;
        while (i<DM.dic.diction.size()){
            System.out.println(i + 1 + "\t\t| " + dictionary.diction.get(i).getWord_target()
                    + "\t\t| " + dictionary.diction.get(i).getWord_explain());
            i++;
        }

    }

    public void dictionaryBasic() {
        DM.GetWordFromDatabase();
        DM.ExportToFile();
        showAllWord(DM.dic);
    }

    public void dictionaryAdvance() {
        DM.insertFromFile();
        showAllWord(DM.dic);
        DM.dictionaryLookup();
    }

    /**
     * Tìm kiếm các từ nhỏ hơn
     */

    public void dictionarySearcher() {
        boolean sosanh = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chọn từ tiếng anh cần tra: ");
        String S1 = scanner.nextLine();
        for (int i = 0; i < DM.dic.diction.size(); i++) {
            if (DM.dic.diction.get(i).getWord_target().contains(S1)) {
                sosanh = true;
                System.out.println(DM.dic.diction.get(i).getWord_target());
            }
        }
        if (!sosanh) {
            System.out.println("Không tìm thấy từ");
        }
    }
}
