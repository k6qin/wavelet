import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
// The one bit of state on the server: a number that will be manipulated by
// various requests.
int num = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Number: %d", num);
        } 
        else {
            System.out.println("Path: " + url.getPath());
            // adding new strings to the list //
            ArrayList<String> list = new ArrayList<>();
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    list.add(parameters[1]);
                }
                return list.toString();
            }
            // querying the list and searching for substrings //
            ArrayList<String> query_list = new ArrayList<>();
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    for (String s: list) {
                        if (s.contains(parameters[1])) {
                            query_list.add(s);
                        }
                    }
                }
            }
            return query_list.toString();
        }
    }
}


class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
