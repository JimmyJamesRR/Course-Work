@@@ Project 3 Comp 282
@@@ By: James Renger
@@@ Referenced Code From Class Notes 3/28
@@@ Purpose of code:
@@@ 1. Read integers from a file named "input.dat" 
@@@ 2. Sort all the numbers
@@@ 3. Print the sorted integers out, one per line, to a file called output.dat
@@@ Must Impliment and use the following functions:
@@@	int read(int filenamelocation, int arrayaddress, int arraysize)
@@@ 	void sort(int arrayaddress, int itemcount)
@@@ 	int findsmallest(int arrayaddress, int start, int end)
@@@ 	void swap(int arrayaddress, int a, int b)
@@@ 	void print(int filenamelocation, int arrayaddress, int itemcount)

@@@ Equ. for equ-als, removes use of OP code, for ease of use.
	.equ SWI_Open, 0x66        @open a file
	.equ SWI_Close,0x68        @close a file
	.equ SWI_PrChr,0x00        @ Write an ASCII char to Stdout
	.equ SWI_PrStr, 0x69       @ Write a null-ending string 
	.equ SWI_PrInt,0x6b        @ Write an Integer
	.equ SWI_RdInt,0x6c        @ Read an Integer from a file
	.equ Stdout,  1            @ Set output target to be Stdout
	.equ SWI_Exit, 0x11        @ Stop execution

.global Main

Main:
	LDR R6,=InFileHandle  	 @ STORE FILENAMELOCATION
	LDR R7,=ArrayAddress 	 @ STORE THE ARRAYADDRESS
	LDR R9,=OutFileHandle	 @ STORE THE FILENAMELOCATION
	MOV R2, #0		 @ INITIALIZE INDEX/ARRAYSIZE	
	BL read			 @ BRANCHED LINK INTO READ FUNCTION	
	MOV R8,R2		 @ STORE ITEMCOUNT IN FIXED REGISTER
	BL sort			 @ BRANCHED LINK TO SORT FUNC	
	MVN R2,#0		 @ LOAD COUNTER SET TO 0 INTO R2 FOR PRINT FUNC
	BL print		 @ RUN THE PRINT FUNCTION
	BAL FileError		 @ PROGRAM SHOULD CLOSE IN PRINT, IF IT CONTINUE TO HERE, PRINT ERROR
	BAL Exit		 @ EXIT PROGRAM

read:
	STMFD sp!, {LR}		 @ STORE LINK BACK TO MAIN
	LDR R0,=InFileName 	 @ SET TO STORE INPUT FROM FILENAME
	mov r1,#0                 @ mode is input
	swi SWI_Open              @ open file for input
	bcs FileError      	  @ Check Carry-Bit (C): if= 1 then ERROR
	BAL readLoop		 @ START THE READING LOOP
readLoop:  @ THIS EVIL LOOP IS WHERE I AM GETTING THE CARRY ERROR...
	MOV R0,R6		  @ SET R0 TO THE INPUT FILE HANDLE  
	LDR R0,[R0]		  @ POINT TO THE ADDRESS 
	SWI SWI_RdInt             @ read the integer into R0
	BCS closeReturn  	  @ EOF REACHED? IF YES, END LOOP				
	ADD R2,R2,#1		  @ INCREASE THE ARRAYSIZE/INDEX VARIABLE EVERY TIME A NUMBER IS READ
	STR R0,[R7,R2,LSL#2]	  @ STORE THE INT INPUT INTO THE NEW INDEX ADDRESS
	LDR R3,[R7,R2,LSL#2]	  @ UPDATE THE STORED ARRAY IN R3	
	BAL readLoop              @ CONTINUE LOOP	
closeReturn:		
	swi SWI_Close
	LDMFD sp!,{PC}

sort:
	STMFD sp!, {LR}		  @  BREADCRUMBS TO FIND OUR WAY HOME
	MOV R2,#0		  @  SET THE STARTING COUNT IN R2
	BAL findSmallest	  @  
findSmallest:
	CMP R2,R8		  @  COMPARE THE COUNT TO THE ARRAY SIZE
	BGE return
	ADD R2,R2,#1		  @  INCREASE THE COUNTER
	STMFD sp!, {R2}		  @  STORE THE COUNTER TO THE STACK
	ADD R3,R2,#1		  @  STORE THE VALUE OF COUNT++
	LDR R2,[R7,R2,LSL #2]	  @  GET THE VALUE AT COUNT FROM THE SORTED ARRAY	  
	LDR R3,[R7,R3,LSL #2]	  @  GET THE VALUE AT COUNT++ FROM THE SORTED ARRAY
	CMP R2,R3		  @  COMPARE VALUE AT COUNT TO COUNT++
	BGT swap		  @  IF COUNT IS GREATER THAN COUNT++ SWAP
	LDMFD sp!,{R2}		  @  RESTORE COUNTER FROM STACK
	BAL findSmallest	  @  CONTINUE LOOP UNTIL ARRAY COMPLETELY SCANNED	  
return:
	LDMFD sp!,{PC}		  @  FOLLOW THE CRUMBS
swap:
	LDMFD sp!, {R2}		  @  GET INDEX OF GREATER VALUE
	STMFD sp!, {R3}		  @  STORE THE LESSER VALUE TO STACK
	STMFD sp!, {R2}		  @  STORE THE LOWER INDEX VALUE
	LDR R3,[R7,R2,LSL #2]     @  LOAD THE GREATER VALUE
	ADD R2,R2,#1		  @  UPDATE THE INDEX TO THE HIGHER INDEX VALUE
	STR R3,[R7,R2,LSL #2]	  @  STR GREATER VALUE AT HIGHER OF THE TWO INDEXS
	LDMFD sp!,{R2}		  @  RESTORE THE LOWER INDEX
	LDMFD sp!,{R3}		  @  GET THE LESSER ORIGINAL VALUE
	STR R3,[R0,R2,LSL #2]	  @  STR GREATER VALUE AT HIGHER OF THE TWO INDEXS
	STMFD sp!, {R2}		  @  RESTORE THE COUNTER TO THE STACK
	BAL findSmallest	  @  RETURN TO FIND SMALLEST
	
print:
	CMP R2,R8		  @  COMPARE THE COUNT TO THE TOTAL ITEMCOUNT
	BGE closeExit		  @  WHEN COUNT EQUALS NUMBER OF ITEMS, CLOSE THE FILE, AND RETURN TO MAIN			  
	mov R0,R9		  @  IDENTIFY THE FILE TO OUTPUT TO
	LDR R1,[R7,R2,LSL#2]	  @  MOVE THE DATA TO BE OUTPUT INTO R1
	swi SWI_PrInt 		  @  OUTPUT THE DATA INTO THE FILE
	ADD R2,R2,#1		  @  INCREASE THE COUNT/INDEX
	BAL print		  @  LOOP UNTIL FINISHED

closeExit:		
	swi SWI_Close
	BAL Exit

FileError:		@  I GOT REALLY SICK OF SEEING THIS
	mov R0, #Stdout
	ldr R1, =FileOpenInpErrMsg
	swi SWI_PrStr 
	bal Exit                  	  @ give up, go to end
Exit:
	swi SWI_Exit               	  @ stop executing 



.data			@  I INTEND TO LEARN MORE ABOUT THIS SECTION
.align	4		@  B/C I'M PRETTY SURE THIS IS STUFF
			@  IS CAUSING MY PROBLEMS...

InFileName:      .asciz   "input.dat"
FileOpenInpErrMsg: .asciz "Failed to open input file \n"
InFileHandle:    .word 0
	.align 4
OutFileName:      .asciz   "output.dat"
FileOpenOutpErrMsg: .asciz "Failed to open input file \n"
	.align 4
OutFileHandle:    .word 0

ArrayAddress: .skip 8200 * 4
	.align 4
EndOfFileMsg:    .asciz   "End of file reached\n"
.end