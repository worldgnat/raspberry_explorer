package testing;

public class ArgsTest {
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			System.out.println("["+i+"]"+args[i]);
		}
		System.out.println(args[0].substring(args[0].indexOf('i')));
	}
}
