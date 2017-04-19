import json
#this is the chess board
inf=open("8q.json")
board=json.loads(inf.read())
board=board["matrix"]
print(board)

#to check if the queen at row,col is under attacked or not
def issafe2(row,col):
	for i in range(8):
		for j in range(8):
			if(board[i][j]==1): #if a queen exists here, then check if it attacks our queen
				if(row==i):
					return False
				if(col==j):
					return False
				if(abs(row-i)==abs(col-j)):
					return False
	return True

#this function will place a queen at a particular col
def place2(col):
	if(col>=8):		#if all 8 queens are placed, then finish
		return True
		print("\t\tCompleted..\n")
	for i in range(8):	#checking for all rows in that column
		if(issafe2(i,col)):	#is it safe?
			board[i][col]=1 #queen is placed here
			if(place2(col+1)==True):	#recursive call to place next queen
				return True
			board[i][col]=0		#if not placed, then backtrack, i.e it sets to zero and the loop iterates to check for next position
	#print("not possible\n")
	return False

#here we are calling functions
if(place2(1)==True):
	print("solution found")
else:
	print("Solution not possible")
print(board)
