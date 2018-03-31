package com.acerchem.core.web.aliyun;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class NonBlockingThreadCounter implements Runnable {

	private boolean stop;

	private char content;
	private char[] input;
	private char[] result;

	public NonBlockingThreadCounter(char content, char[] input, AtomicInteger refer, char[] result) {
		super();
		this.content = content;
		this.input = input;
		this.refer = refer;
		this.result = result;
	}

	private AtomicInteger refer;

	@Override
	public void run() {
		while (!stop) {
			int pos;
			nonBlocking: try {
				while (true) {
					pos = this.refer.get();
					if (this.content == this.input[pos]
							&& this.refer.compareAndSet(pos, pos + 1)) {
						result[pos] = this.content;
						break nonBlocking;
					}
				}
			} catch (Exception e) {
				System.out.println("Stop thread-->"+this.content);
				stop = true;
			}
		}
	}
	
	public static void main(String args[]) {
		char[] order1 = {'A','B','C','B','A','B','A','B','A','C','A','B','A','B','C','B','A','B','A','B','A','B','A','C','A','C'};
		char[] result = new char[order1.length];
		AtomicInteger pos = new AtomicInteger(0);
		ExecutorService executor = Executors.newFixedThreadPool(3);
		NonBlockingThreadCounter thread1 = new NonBlockingThreadCounter('A',order1,pos,result);
		NonBlockingThreadCounter thread2 = new NonBlockingThreadCounter('B',order1,pos,result);
		NonBlockingThreadCounter thread3 = new NonBlockingThreadCounter('C',order1,pos,result);
		NonBlockingThreadCounter thread4 = new NonBlockingThreadCounter('D',order1,pos,result);
		executor.execute(thread1);
		executor.execute(thread2);
		executor.execute(thread3);
		executor.execute(thread4);
		executor.shutdown();
		while(!executor.isTerminated()) {}
		for(char item : result) {
			System.out.print(item+" ");
		}
		
		
	}
}
