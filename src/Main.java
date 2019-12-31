import java.io.*;
import java.util.*;


public class Main {
    static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("search4.in")));
        StringBuilder stringBuilder = new StringBuilder();
        String[] line;
        n = Integer.parseInt(reader.readLine());
        String[] strings = new String[n];
        V[] VforStrings = new V[n];
        String si;
        V root = new V(-1, null);
        for (int i = 0; i < n; i++) {
            si = reader.readLine();
            V v = root;
            for (int j = 0; j < si.length(); j++) {
                int c = si.charAt(j) - 'a';
                if (v.to[c] == null) {
                    v.to[c] = new V(c, v);
                }
                v = v.to[c];
            }
            VforStrings[i] = v;
        }
        ArrayDeque<V> curLevel = new ArrayDeque<>();
        ArrayList<V> order = new ArrayList<>();
        curLevel.addFirst(root);
        while (!curLevel.isEmpty()) {
            V v = curLevel.removeLast();
            order.add(v);
            if (v != root) {
                V suf = v.p.sufLink;
                while (suf != null && suf.to[v.cFrom] == null) {
                    suf = suf.sufLink;
                }
                if (suf == null) {
                    v.sufLink = root;
                } else {
                    v.sufLink = suf.to[v.cFrom];
                }
            }
            for (int i = 0; i < 26; i++) {
                if (v.to[i] != null) {
                    curLevel.addFirst(v.to[i]);
                }
            }
        }

        String T = reader.readLine();
        V v = root;
        for (int i = 0; i < T.length(); i++) {
            int ch = T.charAt(i) - 'a';
            while (v != null && v.to[ch] == null) {
                v = v.sufLink;
            }
            if (v == null) {
                v = root;
            } else {
                v = v.to[ch];
                v.c++;
            }
        }

        for (int i = order.size() - 1; i > 0; i--) {
            V v2 = order.get(i);
            v2.sufLink.c += v2.c;
        }

        FileWriter writer = new FileWriter("search4.out");

        for (int i = 0; i < n; i++) {
            if (VforStrings[i].c > 0) {
                writer.write("YES\n");
//                System.out.println("YES");
            } else {
                writer.write("NO\n");
//                System.out.println("NO");
            }
        }
        writer.close();
    }

    static class V {
        public V(int cFrom, V p) {
            this.cFrom = cFrom;
            this.p = p;
        }

        V sufLink;
        int cFrom;
        int c = 0;
        V p;
        V[] to = new V[26];
    }
}
