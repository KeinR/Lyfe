// #ifndef INPUT__HPP
// #define INPUT__HPP

// #include <iostream>
// #include <unistd.h>
// #include <termios.h>
// #include <thread>

// #include <string>

#include "getchar.hpp"

/*
__declspec(dllexport)
int getch(void) {
   char buf = 0;
   struct termios old = {0};
   fflush(stdout);
   if(tcgetattr(0, &old) < 0)
       perror("tcsetattr()");
   old.c_lflag &= ~ICANON;
   old.c_lflag &= ~ECHO;
   old.c_cc[VMIN] = 1;
   old.c_cc[VTIME] = 0;
   if(tcsetattr(0, TCSANOW, &old) < 0)
       perror("tcsetattr ICANON");
   if(read(0, &buf, 1) < 0)
       perror("read()");
   old.c_lflag |= ICANON;
   old.c_lflag |= ECHO;
   if(tcsetattr(0, TCSADRAIN, &old) < 0)
       perror("tcsetattr ~ICANON");
   return buf;
}
*/

extern int __cdecl speak() {
    // std::cout << "Hi am cpluzppluz" << std::endl;
    int val = 2;
    return val;
}

// #endif
