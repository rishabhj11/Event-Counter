import java.io.*;

public class bbst {

    public static void main(String[] args) throws IOException {
        //long startTime = System.currentTimeMillis();
        File inFile = null;
        if (0 < args.length) {
            inFile = new File(args[0]);
        } else {
            System.err.println("Invalid arguments count:" + args.length);
            System.exit(0);
        }

        rbTree tree = new rbTree();
        BufferedReader br = new BufferedReader(new FileReader(inFile));
        String line = br.readLine();
        int num = Integer.parseInt(line);
        String[] splitData = {""};
        int[] arrayOfIds = new int[num];
        int[] arrayOfCount = new int[num];
        int i = 0;
        while((line = br.readLine())!= null){
            splitData = line.split(" ");
            arrayOfIds[i] = Integer.parseInt(splitData[0]);
            arrayOfCount[i] = Integer.parseInt(splitData[1]);
            i++;
        }


        int maxHeight;
        if(findIfPowerOf2(num)){
            maxHeight = (int)(Math.log10(num)/Math.log10(2)) + 1;
        }
        else {
            maxHeight = (int)(Math.ceil(Math.log10(num)/Math.log10(2)));
        }

        tree.setRoot(tree.buildInitialRBTree(arrayOfIds, arrayOfCount, 0, num - 1, 0, maxHeight));
        tree.getRoot().setColor(true);//Corner condition in case there is only one node

        /*********To calculate the time taken by the program in milliseconds******/
        /*long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Read complete in: "+totalTime+" milliseconds");
        System.out.println("enter cmd");*/

        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader nbr = new BufferedReader(isr);
            while (!(line = nbr.readLine()).equals("quit")) {
                splitData = line.split(" ");
                switch (splitData[0]) {
                    case "increase":
                        tree.increase(Integer.parseInt(splitData[1]), Integer.parseInt(splitData[2]));
                        break;
                    case "reduce":
                        tree.reduce(Integer.parseInt(splitData[1]), Integer.parseInt(splitData[2]));
                        break;
                    case "count":
                        tree.count(Integer.parseInt(splitData[1]));
                        break;
                    case "inrange":
                        tree.inRange(Integer.parseInt(splitData[1]), Integer.parseInt(splitData[2]));
                        break;
                    case "next":
                        tree.next(Integer.parseInt(splitData[1]));
                        break;
                    case "previous":
                        tree.previous(Integer.parseInt(splitData[1]));
                        break;
                    default:
                        System.out.println("Invalid command");
                        break;
                }
            }
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
    public static boolean findIfPowerOf2(int x){
        return (x != 0) && ((x & (x - 1)) == 0);
    }
}