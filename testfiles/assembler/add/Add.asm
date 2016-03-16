// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/06/add/Add.asm

// Computes R0 = 2 + 3

// M[0] = 256

// put value 1 into RAM
@2
D=A // D=2
@0 // A=0
A=M // A=M[0]=256
M=D // M[A]=D => M[256]=2

// increase SP
@0
M=M+1

// put value 2 into RAM
@3
D=A
@0
A=M
M=D
