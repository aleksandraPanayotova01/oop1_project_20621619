public class Save {
    CopyContent copy = new CopyContent();

    public void save() {
        copy.copyContent("temp.xml", "hotel.xml");
        System.out.println("The changes have been saved");
    }

    public void saveAs(String filePath) {
        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            String extension = filePath.substring(i+1);
            if(extension.equals("xml")){
                copy.copyContent("temp.xml", filePath);
                System.out.println("The changes have been saved to " + filePath);
            }
            else {
                System.out.println("Not a xml extension!");
            }
        }
        else
            System.out.println("Please write an extension!");
    }
}

