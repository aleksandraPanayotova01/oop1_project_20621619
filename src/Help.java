public class Help {
    public void help(){
        System.out.println("The following commands are supported: \n" +
                "open <file>\n"+
                "close\n"
                +"save\n"
                +"saveas <file>\n"
                +"checkin <room> <from> <to> <note>(max 2 lines) [<guests>] \n"
                +"availability <date>\n"
                +"checkout <room>\n"
                +"report <from> <to>\n"
                +"find <beds> <from> <to>\n"
                +"unavailable <room> <from> <to> <note>\n"
                +"exit");
    }
    public void menu(){
        System.out.println("***************MENU****************");
        System.out.println("Enter one of the following commands:");
        help();
        System.out.println("*************************************");
    }
}
