import java.io.*;
import java.util.Scanner;
//haha
class poly {
    public int coef, exp;
    public poly next;
}

public class PolyLinkList {
    public static poly ptr;
    public static poly eq1;
    public static poly eq2;
    public static poly ans;

    public static poly readFile(Scanner scanner) {
        poly eq = null;
        String s1 = "", s2 = "";
        while (true) {
            if (!scanner.hasNextLine()) {
                break;
            }

            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                break;
            }
            if (s1.isEmpty()) {
                s1 = line;
                geteq(s1);
            } else {
                s2 = line;
                geteq(s2);
            }
        }
        return eq;
    }

    public static void geteq(String line) {
        poly ptr = null, head = null;
        String[] tokens = line.split("(?=\\+)|(?=-)");
        head = null;
        for (String token : tokens) {
            if (head != null) {
                ptr.next = new poly();
                ptr = ptr.next;
            } else
                head = ptr = new poly();
            String[] tokennode = token.split("x\\^");
            if (tokennode.length == 1) {
                ptr.coef = Integer.parseInt(tokennode[0]);
                ptr.exp = 0;
                break;
            }
            ptr.coef = Integer.parseInt(tokennode[0]);
            ptr.exp = Integer.parseInt(tokennode[1]);
        }
        if (eq1 == null)
            eq1 = head;
        else
            eq2 = head;
    }

    public static void add() {
        poly this1 = eq1, this2 = eq2, prev = null;
        while (this1 != null || this2 != null) {
            ptr = new poly();
            ptr.next = null;

            if ((this1 != null && this2 == null) || (this1 != null && this1.exp > this2.exp)) {
                ptr.coef = this1.coef;
                ptr.exp = this1.exp;
                this1 = this1.next;
            } else if (this1 == null || (this2.exp > this1.exp)) {
                ptr.coef = this2.coef;
                ptr.exp = this2.exp;
                this2 = this2.next;
            } else {
                ptr.coef = this1.coef + this2.coef;
                ptr.exp = this1.exp;
                if (this1 != null)
                    this1 = this1.next;
                if (this2 != null)
                    this2 = this2.next;
            }

            if (ptr.coef != 0) {
                if (ans == null)
                    ans = ptr;
                else
                    prev.next = ptr;
                prev = ptr;
            } else
                ptr = null;
        }
    }

    public static void sub() {
        poly p1 = eq1;
        poly p2 = eq2;

        ans = null;
        poly prev = null;
        while (p1 != null || p2 != null) {
            poly newNode = new poly();
            if (ans == null) {
                ans = newNode;
            } else {
                prev.next = newNode;
            }
            prev = newNode;

            if (p1 != null && p2 != null && p1.exp == p2.exp) {
                newNode.coef = p1.coef - p2.coef; // Subtract coefficients
                newNode.exp = p1.exp;
                p1 = p1.next;
                p2 = p2.next;
            } else if ((p1 != null && p2 != null && p1.exp > p2.exp) || (p2 == null)) {
                newNode.coef = p1.coef;
                newNode.exp = p1.exp;
                p1 = p1.next;
            } else {
                newNode.coef = -p2.coef; // Negate the coefficient for subtraction
                newNode.exp = p2.exp;
                p2 = p2.next;
            }
        }
    }

    public static void print(poly node) {
        if (node.coef < 0)
            System.out.printf("%dx^%d", node.coef, node.exp);
        else
            System.out.printf("%dx^%d", node.coef, node.exp);
        node = node.next;
        while (node != null) {
            if (node.exp == 0) {
                if (node.coef > 0)
                    System.out.printf("+%d", node.coef);
                else
                    System.out.printf("%d", node.coef);
            } else if (node.coef > 0)
                System.out.printf("+%dx^%d", node.coef, node.exp);
            else
                System.out.printf("%dx^%d", node.coef, node.exp);
            node = node.next;
        }
    }

    public static void main(String[] args) {
        try {
            File file = new File("input.txt");
            Scanner fileScanner = new Scanner(file);
            readFile(fileScanner);
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("檔案未找到：" + e.getMessage());
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("多項式1: ");
        print(eq1);
        System.out.println();
        System.out.print("多項式2: ");
        print(eq2);
        System.out.println();
        System.out.print("選擇 1相加/2相減:");
        int mode = sc.nextInt();
        if (mode == 1) {
            System.out.print("多項式1+多項式2= ");
            add();
        } else {
            System.out.print("多項式1-多項式2= ");
            sub();
        }

        print(ans);
    }
}
