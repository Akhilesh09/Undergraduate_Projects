#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include<ctype.h>

/* run this program using the console pauser or add your own getch, system("pause") or input loop */
void fn_menu();
int f_airlines_seatconfig();
int f_airlines_display();
int f_airlines_book();
int f_airlines_cancel();
char cha_filename[120]={"datafile.txt"};
//char cha_airlinesinfo[120]={"airlinesinfo.txt"};
char cha_booking_info[120]={"bookdata.txt"};
char sm[10][10];
char c;
void main() {
//int main() {
int i_optno,i_setupresult,i_configresult,i_displayresult,i_bookresult,i_cancelresult;
fn_menu();
while(1)
{
	printf("\tEnter the desired option number 0-6:");
    scanf("%d",&i_optno);
switch(i_optno){
	case 0:{
		printf("CHOICE-0:Exit the Airlines application\n");
		break;
	  }
     case 1:{
		printf("\nCHOICE-1:Configure the seating arrangment for airlines\n");
		i_configresult=f_airlines_seatconfig();     // confgure the seating arrangment for airlines
		if(i_configresult==999)
		printf("\nConfiguration file on seating arrangement successfuly completed for the airlines\n");
		if(i_configresult==99)
		printf("\nError occured while opening the configuration file\n");
		if(i_configresult==9)
		printf("\nConfiguration file could not be created for the requested airlines\n");
		break;
	  }

      case 2:{
		printf("\nCHOICE-2:Display the seating arrangment for airlines\n");
		i_displayresult=f_airlines_display();   // display the seating arrangment for airlines
		if(i_displayresult==999)
		printf("\nDisplay of airlines seating arrangment successfully completed\n");
		if(i_displayresult==99)
		printf("\nError occured while opening the airlines configuration file\n");
		if(i_displayresult==9)
		printf("\nConfiguration file for seating arrangement not available please create now\n");
		break;
	  }
	  case 3:{
		printf("CHOICE-3:Book the seat on the selected airlines\n");
		i_displayresult=f_airlines_display();   // display the seating arrangment for airlines
		if(i_displayresult==999){
		printf("\nDisplay of airlines seating arrangment successfully completed\n");
		printf("\nYou may now proceed to book the seat as per the instructions below\n");
		i_bookresult=f_airlines_book();   // book seat on the selected airlines
				 }
		if(i_displayresult==99){
		printf("\nCannot proceed with seat booking, due to error while opening the airlines configuration file\n");
				}
		if(i_displayresult==9){
		printf("\nCannot proceed with seat booking,Configuration file for seating arrangement not available please create now\n");
				}
		break;
	     }
	  case 4:{
		printf("CHOICE-4:Cancel the booked seating on the selected airlines\n");
		i_displayresult=f_airlines_display();   // display the seating arrangment for airlines
		if(i_displayresult==999){
		printf("\nDisplay of airlines seating arrangment successfully completed\n");
		printf("\nYou may now proceed to cancel the seat as per the instructions below\n");
		i_cancelresult=f_airlines_cancel();     // Cancel the booked seating on the selected airlines
				 }
		if(i_displayresult==99){
		printf("\nCannot proceed with seat cancellation, due to error while opening the airlines configuration file\n");
				}
		if(i_displayresult==9){
		printf("\nCannot proceed with seat cancellation,Configuration file for seating arrangement not available please create now\n");
				}

		break;
	  }
		  case 6:
		  exit(0);
		  case 5:
			{
				FILE *fp_userinfo;
				int colno=1,irowno,s;
				char rowno;
				char cha_fullname[20];
				fp_userinfo = fopen(cha_booking_info, "r"); // error check this!cha_booking_info
	if (fp_userinfo==NULL){
    printf("File could not be opened\n");
    return 9; // error file could not be created with the name given
	}
    else if (ferror(fp_userinfo)!=0) {
    printf("An error occured while opening the file\n"); //error in updating the booking into the file
    return 99; // error as while opening the file, write permission issue
	}
    else  //valid file name able to open successfull
    {
	printf("AIRLINE BOOKING INFO\n");
    printf("%20s\t%4s\n","NAME","SEAT");
	while(!feof(fp_userinfo))
	{
	fscanf(fp_userinfo,"%20s\t%d\t%d\t%d\n",cha_fullname,&irowno,&colno,&s);
	rowno='@'+irowno;
	printf("%20s\t%c%d\n",cha_fullname,rowno,colno);
}
	}
    fclose(fp_userinfo);
    break;
			  }

	default:{
	     printf("You have typed an invalid option number.\n");
	     exit(0);
	     break;
	    }
} // end of switch statement
}
getch();

//system("pause");
//return 0;
}
//****************************************************************
//** All user defined functionality has been defined below//
//*****************************************************************

/***********************************************************************************/
/*** PRESENTING THE ARLINES APPLICATION MENU
// THE FUNCTIONALITY IS BUILT MODULARLY
// EACH FUNCTIONALITY ACTIVATED BASED ON THE USER CHOICE OF THE MENU
// ALL MANIPULATION OF DATA IS DONE IN MEMORY
// EVERY DATA MANIPULATION IN MEMORY IS UPDATED TO FILE AFTER ALL VALIDATION IS COMPLETED.
/**********************************************************************************/
void fn_menu(){
    printf("------------------------------------------------------\n");
    printf("---WELCOME TO AIRLINES RESERVATION CX4235 APPLICATION SYSTEM ---\n");
    printf("------------------------------------------------------\n");
    printf(" AIRLINES RESERVATION SYSTEM APPLICATION MENU\n");
    printf("\tCHOICE 1: CONFIGURE THE AIRLINES NAME AND SEATING ARRANGEMENT\n");
    printf("\tCHOICE 2: DISPLAY THE AIRLINES SEATING ARRANGEMENT\n");
    printf("\tCHOICE 3: BOOK SEAT ON THE AIRLINES\n");
    printf("\tCHOICE 4: CANCEL THE SEAT BOOKED \n");
  //  printf("\tCHOICE 5: CONFIGURE AIRLINES NAME, SEATING CAPACITY, FLIGHT WEEK DAYS\n");
   // printf("\tCHOICE 6: UPDATE USER TICKET INFO\n");
   printf("\tCHOICE 5:PRINT DATABASE\n");
    printf("\tCHOICE 6: EXIT THE APPLICATION \n");
    

}




/***********************************************************************************/
/*** setup the aircraft seat arrangement
// limit the number row to 9
// limit the number of columns to 10
// 0th column is used for storing the no of seats available for booking in that row
/**********************************************************************************/
int f_airlines_seatconfig(){
	FILE *fp_setup;
	int i=0;
	char rowstring[]="1234567890";
	char seatstring[]="NNNNNNNNN9";
	fp_setup = fopen(cha_filename, "w"); // error check this!
    if (fp_setup==NULL){
    printf("File could not be opened\n");
    return 9; // error as file does not exist or could not be created.
	}
    else if (ferror(fp_setup)!=0) {
    printf("An error occured while opening the file\n"); //error in reading the file
    return 99; // error as while opening the file for write (permission issue
	}
    else  //valid file name able to open successfully
    {
    fprintf(fp_setup,"%s\n",rowstring);  //old number & name
    for(i=0;i<10;i++)
    {

	fprintf(fp_setup,"%s\n",seatstring);
	}
	}
    fclose(fp_setup);
    return 999; // successful airlines configuration completed.
}
/***********************************************************************************/
/**Display the airlines seat configuration from file
/**configuration file must exist to display the screen
/**airlines setup must be done prior to dispaying the configuration
/** on screen and prior to taking the bookings
/************************************************************************************/
int f_airlines_display(){
	FILE *fp_display;
	int i=0, j=0;
	char c1='A'-1;
	fp_display = fopen(cha_filename, "r"); // open the existing config file in read mode
    if (fp_display==NULL){
    printf("File could not be opened\n");
    return 9; // error as file does not exist
	}
    else if (ferror(fp_display)!=0) {
    printf("An error occured while opening the file\n"); //error in reading the file
    return 99; // error as while opening the file, read permission issue
	}
    else
    {
    i=0;j=0;
    while(((c = fgetc(fp_display)) != EOF) && (i <=10 ) && (j <=10) )
	{
			if(c!='\n')
						{
			sm[i][j]=c;
					    j++;
				//		printf("read char %c   %d \n",c,j);
						}
						else
						{
						j=0;
						i++;
					//	printf("row no %d\n",i);
						}
    }
    fclose(fp_display);  ///close the file after laoding the configuration to the memory
    // print the seating arrangement and status on screen
	for (i=0; i<10; i++) {
		printf("%c\t",c1);
		c1++;
	for (j=0; j<10; j++) {
	if(sm[i][j]!='\n')
	printf("%c\t",sm[i][j]);
	}
	printf("\n");
	}
	}
	return 999;	// successful airlines configuration loaded into memory buffer
}
/***********************************************************************************/
/** booking and updating the new configuration
/** you can book only one seat at a time
/** you need to specify the row no and column no to book a specfic seat.
/** the first column count indicates the seat vacancy in that particular row
/**seating only starts from column 1 for all rows and ends on column 9
/************************************************************************************/
int f_airlines_book(){
	char rowno;
	int colno=1,irowno,s=0;
	FILE *fp_book;
		FILE *fp_userinfo;
	char cha_fullname[20];
	int i=0,j=0;
	printf("Specify the row no for the seat   (1 to 9 only):\n");
	scanf("%1s",&rowno);
	while(toupper(rowno)>='J')
	{
	printf("invalid rowno\n");
	printf("Specify the row no for the seat   (1 to 9 only):\n");
	scanf("%1s",&rowno);
	}
	printf("Specify the column no of the seat (1 to 9 only):\n");
	scanf("%d",&colno);
	while(colno>=10)
	{
	printf("invalid rowno\n");
	printf("Specify the row no for the seat   (1 to 9 only):\n");
	scanf("%d",&colno);
	}
	irowno =toupper(rowno)-'@';
	rowno =toupper(rowno)-'@';

    //printf("rowno= %d",irowno);
	while(sm[irowno][9]=='0')
	{
	printf("row full\n");
	printf("Specify the row no for the seat   (A to I only):\n");
	scanf("%1s",&rowno);
	printf("Specify the column no of the seat (1 to 9 only):\n");
	scanf("%d",&colno);
	irowno =toupper(rowno)-'@';
	rowno =toupper(rowno)-'@';
	}
	if(sm[rowno][colno-1]=='B')
	{
		printf("seat already booked\n");
		return 999;
	}
	printf("Name of the Person   (max 20 chars):\n");
	scanf("%20s",cha_fullname);
	printf("%20s\t seatno:%c%d\n",strupr(cha_fullname),toupper(rowno)+'@',colno);
	fp_userinfo = fopen(cha_booking_info, "a"); // error check this!cha_booking_info
	if (fp_userinfo==NULL){
    printf("File could not be opened\n");
    return 9; // error file could not be created with the name given
	}
    else if (ferror(fp_userinfo)!=0) {
    printf("An error occured while opening the file\n"); //error in updating the booking into the file
    return 99; // error as while opening the file, write permission issue
	}
    else  //valid file name able to open successfull
    {
	fprintf(fp_userinfo,"%20s\t%d\t%d\t%d\n",strupr(cha_fullname),rowno,colno,s);
	}
    fclose(fp_userinfo);
	sm[rowno][colno-1]='B';
	sm[rowno][9]=sm[rowno][9]-1;  //balance seats
	fp_book = fopen(cha_filename, "w"); // error check this!
	if (fp_book==NULL){
    printf("File could not be opened\n");
    return 9; // error file could not be created with the name given
	}
    else if (ferror(fp_book)!=0) {
    printf("An error occured while opening the file\n"); //error in updating the booking into the file
    return 99; // error as while opening the file, write permission issue
	}
    else  //valid file name able to open successfully
    { // update the booking details seat by seat into the file
	for(i=0;i<10;i++){
	for(j=0;j<10;j++){
	putc(sm[i][j], fp_book);
	}
    putc('\n', fp_book);
    }
	}
    fclose(fp_book);  // close the file after the booking is updated.
    return 999;
}
/***********************************************************************************/
/** updating user ticket information
/** you will update name, dob, flight no, seat no, booking day
/** you need to specify the row no and column no to book a specfic seat.
/** the first column count indicates the seat vacancy in that particular row
/** seating only starts from column 1 for all rows and ends on column 9
/************************************************************************************/
/***********************************************************************************/
/** cancelling and updating the new configuration
/** you can cancel only the booked seat one seat at a time
/** you need to specify the row no and column no to book a specfic seat.
/** the first column count indicates the seat vacancy in that particular row
/** seating only starts from column 1 for all rows and ends on column 9
/************************************************************************************/
int f_airlines_cancel(){
	 struct
{
char name[20];
char rowno;
int colno,s;
}t2[20],t[20];
	char rowno;
	int k,x=0,s=0;
	int irowno,colno=1;
	FILE *fp_cancel;
	int i=0,j=0,m;
	char name[20];
	printf("Specify the row no for the seat   (1 to 9 only):\n");
	scanf("%1s",&rowno);
	while(toupper(rowno)>='J')
	{
	printf("invalid rowno\n");
	printf("Specify the row no for the seat   (1 to 9 only):\n");
	scanf("%1s",&rowno);
	}
	printf("Specify the column no of the seat (1 to 9 only):\n");
	scanf("%d",&colno);
	while(colno>=10)
	{
	printf("invalid rowno\n");
	printf("Specify the row no for the seat   (1 to 9 only):\n");
	scanf("%d",&colno);
	}
	printf("enter name:");
	scanf("%20s",name);
	if(sm[rowno][colno-1]=='N')
	{
		printf("seat already cancelled\n");
		return 999;
	}
	irowno =toupper(rowno)-'@';
	rowno =toupper(rowno)-'@';
	sm[rowno][colno-1]='N';
	sm[rowno][9]=sm[rowno][9]+1;  //balance seats
	fp_cancel = fopen(cha_filename, "w"); // error check this!
	if (fp_cancel==NULL){
    printf("File could not be opened\n");
    return 9; // error file could not be created with the name given
	}
    else if (ferror(fp_cancel)!=0) {
    printf("An error occured while opening the file\n"); //error in updating the booking into the file
    return 99; // error as while opening the file, write permission issue
	}
    else  //valid file name able to open successfully
    { // update the booking details seat by seat into the file
	for(i=0;i<10;i++){
	for(j=0;j<10;j++){
	putc(sm[i][j], fp_cancel);
	}
    putc('\n', fp_cancel);
    }
     fclose(fp_cancel);

    fp_cancel=fopen("bookdata.txt","r");
    m=0;k=0;
    while(!feof(fp_cancel))
    {
    fscanf(fp_cancel,"%20s\t%d\t%d\t%d",t[k].name,&t[k].rowno,&t[k].colno,&t[k].s);
    x++;
    k++;
    }
    fclose(fp_cancel);
    fp_cancel=fopen("bookdata.txt","r");
    
while((!feof(fp_cancel))&&(s!=1))
{
fscanf(fp_cancel,"%20s\t%d\t%d\t%d",t2[m].name,&t2[m].rowno,&t2[m].colno,&t2[m].s);
if((strcmp(strupr(t2[m].name),strupr(name))==0)&&(t2[m].rowno==toupper(rowno))&&(t2[m].colno==colno))
if(t2[m].s==0)
{
t2[m].s=1;
s=1;
}
m++;
}
fclose(fp_cancel);
    fp_cancel=fopen("bookdata.txt","w");
for(j=0;j<m-1;j++)
fprintf(fp_cancel,"%20s\t%d\t%d\t%d\n",t2[j].name,t2[j].rowno,t2[j].colno,t2[j].s);
printf("HERE ARE THE DETAILS\n%s %d %d \nBOOKING CANCELLED MR.%s\n",t2[j].name,t2[j].rowno,t2[j].colno,t2[j].name);
for(j=m;j<x-1;j++)
fprintf(fp_cancel,"%20s\t%d\t%d\t%d\n",t[j].name,t[j].rowno,t[j].colno,t[j].s);
	}
    fclose(fp_cancel);  // close the file after the booking is updated.
    return 999;
}
