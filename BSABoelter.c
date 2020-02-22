#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>

//Function Prefixes:
void findLocationFast(char *filename, char *prefix, long start, long middle, long end, int schalter);

int main(int argc, char **argv)
{
//handle errors here for file name and for prefix length

//call findLocationFast
//filename input first, prefix second
//wc- nanpa to find lines in the file = 166,482
//166,482 * 32 = 5,327,424; 5,327,424 / 2 = 2,663,712 bytes 
	long start = 0;	//start of bytes
	long middle = 0;//middle in bytes
	long end = 0;//end of bytes
	int schalter = 0;//switch to see if the total file size in bytes has been found. Switched to 1 if total size is found
	int f = 0;
	int oFile = open(argv[1],O_RDWR);
	end = lseek(oFile,start,SEEK_END);
	if(end == -1)//file doesn't exist or non seekable
	{
		write(1,"Error: File not seekable or doesn't exist",41);
		close(oFile);
		return -1;
	}
	close(oFile);
	int openFile = open(argv[1],O_RDONLY);
	end = lseek(openFile,start,SEEK_END);
	if(start>end)//file doesn't exist or is nonseekable
	{
		write(1,"Error: File not seekable or doesn't exist",41);
		close(openFile);
		return -1;
	}
	close(openFile);
	findLocationFast(argv[1], argv[2], start, middle, end, schalter);//calling function for first time
	return 0;
}

void findLocationFast(char *filename, char *prefix, long start, long middle, long end, int schalter)
{	
	//use a binary search algorithm to keep O(log n)
	//since all lines are the same length (32 chars)
	//we can use lseek in a BSA recursivley
	char low[7];
	char hi[7];
	char pre[20];

	for(int i = 0; i<20; i++)//setting chars of pre to ' '
	{
		pre[i] = ' ';
	}
	pre[6] = 'P';
	for(int i = 0; i<6; i++)//putting '0' into string of 6 to check for digit values
	{
		low[i] = '0';
	}
	for(int i = 0; i<6; i++)//putting '9' into string of 6 to check for digit values
	{
		hi[i] = '9';
	}
	for(int i = 0; i<20; i++)//putting the prefix into a string
	{
		pre[i] = prefix[i];
	}
	for(int i = 0; i<6; i++)
	{

		if(low[i]>pre[i])//checking if less than 000000 ie, non numerical chars
		{
			write(1,"Error: Not an six digit number",30);
			return -1;
		}
		if(hi[i]< pre[i])//checking if greater than 99999
		{
			write(1,"Error: Not an six digit number",30);
			return -1;
		}
		if(pre[6] > ' ')//checking to see if anything exists past char 6
		{
			write(1,"Error: Not an six digit number",30);
			return -1;
		}	
	
		
	}
	int openFile = open(filename,O_RDONLY);//opening file

	if(schalter == 0)//switching
	{
		//wc- nanpa to find lines in the file = 166,482
		//166,482 * 32 = 5,327,424; 5,327,424 / 2 = 2,663,712 bytes 
		end = lseek(openFile,start,SEEK_END);
		middle = (end/2);
		schalter = 1;
	}
	if(start>=end)//if there is nothing in the file or the start ends up greater than end
	{
		return -1;
	}

	else
	{
	
		int openFile = open(filename,O_RDONLY);
		//read the first 32 characters at 1 byte each
		//search the first half and last half of the file
		 	
		char buffer[7];//make last character equal to null.
		buffer[6] = '\0';

		lseek(openFile, middle, SEEK_CUR); 
		read(openFile, buffer ,6);

		char *buff = buffer;//pointer to buffer

		if((strcmp(prefix, buff)) == 0)//Found prefix
		{
			//following code strips extra space before writing to the terminal screen
			char bigBuff[31];
			read(openFile, bigBuff,25);
			int j = 0;
			for(int i = 0;i<25;i++)
			{
				if(bigBuff[i] != ' ')
				{
					j++;
				}
				else
				{
					if(bigBuff[i+1] != ' ')
					{
						j++;
					}
				}
			}
			int m = j-1;
			char smallBuff[m];
			for(int i = 0; i <m; i++)
			{
				smallBuff[i] = bigBuff[i];
			}
			write(1,smallBuff,m);
			close(openFile);
			return 0;

		}

		if((strcmp(prefix, buff)) > 0)//Search last half
		{

			long end32 = end/32;
			long mid32 = middle/32;	
			if((end32-mid32) <=1)
			{	
				close(openFile);//Prevents segementation fault if prefix doesn't exist
				return -1;
			}
			long diff = end32-mid32;
			diff = (diff/2)+mid32;
			start = mid32*32;
			diff = diff*32;
			end = end32*32;
			findLocationFast(filename, prefix, start, diff, end, schalter);//calls for last half
			
		}

		if((strcmp(prefix, buff)) < 0)//Search first half
		{
			long middle32 = middle/32;
			long difference = middle32/2;
			long start32 = start/32;
			if((middle32-start32) <=1)			
			{
				close(openFile);//Prevents segementation fault if prefix doesn't exist
				return -1;
			}
			difference = ((difference*32));
			findLocationFast(filename, prefix, start, difference, middle, schalter);//calls for last half
			
		}

	}
}




