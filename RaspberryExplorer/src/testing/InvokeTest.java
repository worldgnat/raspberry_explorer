package testing;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class InvokeTest {
	public InvokeTest() {
		
	}
	
	public void metaGo() {
		Integer test = new Integer(5);
		MethodType mt;
		MethodHandle mh;
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		mt = MethodType.methodType(boolean.class, Object[].class);
		try {
			mh = lookup.findVirtual(this.getClass(), "go", mt);
			mh.invoke(this, test);
			//this.getClass().getMethod("go", Object[].class).invokeWithArguments( test);
		}
		catch (Exception er) {
			er.printStackTrace();
		}
		catch (Throwable e) {
			
		}
	}
	
	public boolean go(Object... args) {
		System.out.println("We got a " + args.getClass() + "!");
		System.out.println((Integer)args[0]);
		return true;
	}
	
	public static void main(String[] args) {
		new InvokeTest().metaGo();
	}
}
