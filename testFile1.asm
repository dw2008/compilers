	.data
	varx: .word 0
	vary: .word 0
	varcount: .word 0
	.text
	.globl main
	main:   #Mars will automatically look for main
	li $v0 2
	sw $v0, varx
	la $t0 varx
	lw $v0 ($t0) #puts value of variable in $v0
	subu $sp $sp 4
	sw $v0 ($sp) #push reg onto the Stack
	li $v0 1
	lw $t0 ($sp) #pop the Stack into reg
	addu $sp $sp 4
	addu $v0 $t0 $v0
	sw $v0, vary
	la $t0 varx
	lw $v0 ($t0) #puts value of variable in $v0
	subu $sp $sp 4
	sw $v0 ($sp) #push reg onto the Stack
	la $t0 vary
	lw $v0 ($t0) #puts value of variable in $v0
	lw $t0 ($sp) #pop the Stack into reg
	addu $sp $sp 4
	addu $v0 $t0 $v0
	sw $v0, varx
	la $t0 varx
	lw $v0 ($t0) #puts value of variable in $v0
	subu $sp $sp 4
	sw $v0 ($sp) #push reg onto the Stack
	la $t0 vary
	lw $v0 ($t0) #puts value of variable in $v0
	lw $t0 ($sp) #pop the Stack into reg
	addu $sp $sp 4
	mult $t0 $v0
	mflo $v0
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11   #print character
	li $a0 0xA  #load ASCII code for new line
	syscall
	la $t0 varx
	lw $v0 ($t0) #puts value of variable in $v0
	subu $sp $sp 4
	sw $v0 ($sp) #push reg onto the Stack
	la $t0 vary
	lw $v0 ($t0) #puts value of variable in $v0
	lw $t0 ($sp) #pop the Stack into reg
	addu $sp $sp 4
	ble $t0, $v0, endif1
	la $t0 varx
	lw $v0 ($t0) #puts value of variable in $v0
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11   #print character
	li $a0 0xA  #load ASCII code for new line
	syscall
	la $t0 vary
	lw $v0 ($t0) #puts value of variable in $v0
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11   #print character
	li $a0 0xA  #load ASCII code for new line
	syscall
endif1:
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp) #push reg onto the Stack
	li $v0 14
	lw $t0 ($sp) #pop the Stack into reg
	addu $sp $sp 4
	bne $t0, $v0, endif2
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp) #push reg onto the Stack
	li $v0 14
	lw $t0 ($sp) #pop the Stack into reg
	addu $sp $sp 4
	beq $t0, $v0, endif3
	li $v0 3
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11   #print character
	li $a0 0xA  #load ASCII code for new line
	syscall
endif3:
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp) #push reg onto the Stack
	li $v0 14
	lw $t0 ($sp) #pop the Stack into reg
	addu $sp $sp 4
	bgt $t0, $v0, endif4
	li $v0 4
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11   #print character
	li $a0 0xA  #load ASCII code for new line
	syscall
endif4:
endif2:
	li $v0 15
	subu $sp $sp 4
	sw $v0 ($sp) #push reg onto the Stack
	li $v0 14
	lw $t0 ($sp) #pop the Stack into reg
	addu $sp $sp 4
	ble $t0, $v0, endif5
	li $v0 5
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11   #print character
	li $a0 0xA  #load ASCII code for new line
	syscall
endif5:
	li $v0 1
	sw $v0, varcount
loop6:
	la $t0 varcount
	lw $v0 ($t0) #puts value of variable in $v0
	subu $sp $sp 4
	sw $v0 ($sp) #push reg onto the Stack
	li $v0 15
	lw $t0 ($sp) #pop the Stack into reg
	addu $sp $sp 4
	bgt $t0, $v0, endwhile6 #jump to endwhile if done
	la $t0 varcount
	lw $v0 ($t0) #puts value of variable in $v0
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11   #print character
	li $a0 0xA  #load ASCII code for new line
	syscall
	la $t0 varcount
	lw $v0 ($t0) #puts value of variable in $v0
	subu $sp $sp 4
	sw $v0 ($sp) #push reg onto the Stack
	li $v0 1
	lw $t0 ($sp) #pop the Stack into reg
	addu $sp $sp 4
	addu $v0 $t0 $v0
	sw $v0, varcount
	j loop6 #jump back
endwhile6:
	li $v0 10       # normal termination
	syscall
