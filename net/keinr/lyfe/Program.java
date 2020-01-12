package net.keinr.lyfe;

class Program {

    static void start() {
        System.out.println("Ok");
        System.out.println(Thread.currentThread().getName());
        throw new Error("Ah, shit! My fingers slipped~");
    }
}
