#define MAX 4
#define MIN 2
#include<iostream.h>
#include<fstream.h>
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<conio.h>
#include<iomanip.h>
#include<graphics.h>
#include<ctype.h>
#define max 10
#define datafile1 "q.txt"
#define indfile1 "r.txt"
#define datafile2 "u.txt"
#define indfile2 "v.txt"
#define treefile1 "b1.txt"
#define treefile2 "b2.txt"

fstream ifile1,sfile1,ifile2,sfile2,&ifile=ifile1,&sfile=sfile1,bfile1,bfile2,&bfile=bfile1;
int i,f,f1,f2,ch,h,p,x,i1=0,i2=0,r,y,ad,k=0,z,n,d,q;
float cgpa;
char buffer[80],datafile[10],indfile[10],treefile[10];

class student
{
public:
char usn[15];
int sub1ia,sub2ia,sub1ea,sub2ea,sub3ia,sub3ea,sub4ia,sub4ea,sub5ia,sub5ea,sub6ia,sub6ea,lab1ia,lab1ea,lab2ia,lab2ea;
float sgpa;
void read();
void pack();
void unpack();
void upperto();
}s;
class index
{
public:
char iusn[15],addr[5];
void initial();
void write();
};

class btreeNode {
public:
index val[MAX + 1];
int count;
btreeNode *link[MAX + 1];
};
btreeNode *rootx,*rooty,*root;

  void opener(fstream &ifile,char *fn,int mode)
{
ifile.open(fn,mode);
if(!ifile)
{
cout<<"error"<<endl;
return;
}
}

int rsgpa(int t1)
{
if(t1>=90)
return 10;
else if(t1>=80)
return 9;
else if(t1>=70)
return 8;
else if(t1>=60)
return 7;
else if(t1>=50)
return 6;
else if(t1>=40)
return 5;
else if(t1>=30)
return 4;
else if(t1>=20)
return 3;
else if(t1>=10)
return 2;
else
return 1;
}
void student::pack()
{
char a[5];
int o=1;
while(o)
{
cout<<"\t\tEnter Sub1 IA marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
sub1ia=atoi(a);
if(sub1ia>20)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Sub2 IA marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
sub2ia=atoi(a);
if(sub2ia>20)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Sub3 IA marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
sub3ia=atoi(a);
if(sub3ia>20)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Sub4 IA marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
sub4ia=atoi(a);
if(sub4ia>20)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Sub5 IA marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
sub5ia=atoi(a);
if(sub5ia>20)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Sub6 IA marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
sub6ia=atoi(a);
if(sub6ia>20)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Lab1 IA marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
lab1ia=atoi(a);
if(lab1ia>20)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Lab2 IA marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
lab2ia=atoi(a);
if(lab2ia>20)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Sub1 EXT marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
sub1ea=atoi(a);
if(sub1ea>80)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Sub2 EXT marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
sub2ea=atoi(a);
if(sub2ea>80)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Sub3 EXT marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
sub3ea=atoi(a);
if(sub3ea>80)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Sub4 EXT marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
sub4ea=atoi(a);
if(sub4ea>80)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Sub5 EXT marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
sub5ea=atoi(a);
if(sub5ea>80)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Sub6 EXT marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
sub6ea=atoi(a);
if(sub6ea>80)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Lab1 EXT marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
lab1ea=atoi(a);
if(lab1ea>80)
o=1;
else o=0;
}
}
o=1;
while(o)
{
cout<<"\t\tEnter Lab2 EXT marks:";
cin>>a;
if(isalpha(a[0])!=0 || isalpha(a[1])!=0 || strlen(a)>2)
o=1;
else
{
lab2ea=atoi(a);
if(lab2ea>80)
o=1;
else o=0;
}
}

int t1=(sub1ia+sub1ea);
int sgpa1=rsgpa(t1);
int t2=(sub2ia+sub2ea);
int sgpa2=rsgpa(t2);
int t3=(sub3ia+sub3ea);
int sgpa3=rsgpa(t3);
int t4=(sub4ia+sub4ea);
int sgpa4=rsgpa(t4);
int t5=(sub5ia+sub5ea);
int sgpa5=rsgpa(t5);
int t6=(sub6ia+sub6ea);
int sgpa6=rsgpa(t6);
int t7=(lab1ia+lab1ea);
int sgpa7=rsgpa(t7);
int t8=(lab2ia+lab2ea);
int sgpa8=rsgpa(t8);

sgpa=(float)(sgpa1+sgpa2+sgpa3+sgpa4+sgpa5+sgpa6+sgpa7+sgpa8)/8;
//cout<<val.sgpa<<endl;
char ch[2];
strcpy(buffer,usn);
strcat(buffer,"|");
itoa(sub1ia,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(sub2ia,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(sub3ia,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(sub4ia,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(sub5ia,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(sub6ia,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(lab1ia,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(lab2ia,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(sub1ea,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(sub2ea,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(sub3ea,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(sub4ea,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(sub5ea,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(sub6ea,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(lab1ea,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
itoa(lab2ea,ch,10);
strcat(buffer,ch);
strcat(buffer,"|");
char st1[5],st2[5];
//cout<<sgpa<<endl; cout<<(int)sgpa<<endl;
int l=(int)sgpa;
int x=(sgpa-(int)sgpa)*100;
itoa(l,st1,10);
//cout<<st1<<endl;
itoa(x,st2,10);
//cout<<st2<<endl;
strcat(st1,".");
strcat(st1,st2);
//cout<<st1<<endl;
strcat(buffer,st1);
strcat(buffer,"|");
sfile<<buffer<<endl;
}
  /* creating new node */
  btreeNode * createNode(index &val,btreeNode *child) {
	btreeNode *newNode;
 //    cout<<"hi\n";
	newNode = (btreeNode *)malloc(sizeof(btreeNode));
  //	cout<<val.iusn<<"\t"<<val.addr<<endl;
	strcpy(newNode->val[1].iusn,val.iusn);
	strcpy(newNode->val[1].addr,val.addr);
    //	cout<<newNode->val[1].iusn<<newNode->val[1].addr<<endl;
    if(p==1 && n==0)
    {
	 s.pack();

		cout<<"\t\tRecord Inserted \n";
	}

	newNode->count = 1;
	newNode->link[0] = root;
	newNode->link[1] = child;
   //	cout<<newNode->link[0]<<newNode->link[1]<<endl;
	return newNode;
  }

  /* Places the value in appropriate position */
  void addValToNode(index val,int pos, btreeNode *node,btreeNode *child) {
	int j = node->count;
    //	cout<<"hello\n";

	while (j > pos) {
	node->val[j + 1] = node->val[j];

		strcpy(node->val[j + 1].iusn,node->val[j].iusn);
		strcpy(node->val[j + 1].addr,node->val[j].addr);
		node->link[j + 1] = node->link[j];
    //	    cout<<node->link[j + 1]<<endl;

		j--;
	}
	strcpy(node->val[j + 1].iusn,val.iusn);
	strcpy(node->val[j + 1].addr,val.addr);
   //   cout<<node->val[j + 1].iusn<<node->val[j + 1].addr<<endl;
		if(p==1 && n==0)
{
   s.pack();
	cout<<"\t\tRecord Inserted\n";
	n=1;
  }

	node->link[j + 1] = child;
    //	cout<<node->link[j + 1]<<endl;
	node->count++;
  }

  /* split the node */
  void splitNode (index val,index *pval, int pos, btreeNode *node,
     btreeNode *child, btreeNode **newNode) {
	int median, j;
   //   cout<<"yes\n";

	if (pos > MIN)
		median = MIN + 1;
	else
		median = MIN;

	*newNode = (btreeNode *)malloc(sizeof(btreeNode));

	j = median + 1;
	while (j <= MAX) {
	 (*newNode)->val[j - median] = node->val[j];
	 (*newNode)->link[j - median] = node->link[j];
	       strcpy((*newNode)->val[j - median].iusn,node->val[j].iusn);
	       strcpy((*newNode)->val[j - median].addr,node->val[j].addr);
      //	      cout<< (*newNode)->val[j - median].iusn<<(*newNode)->val[j - median].addr<<endl;

		j++;
	}
	node->count = median;
	(*newNode)->count = MAX - median;

	if (pos <= MIN) {
		addValToNode(val, pos, node, child);
	} else {
		addValToNode(val, pos - median, *newNode, child);
	}
     strcpy(pval->iusn,node->val[node->count].iusn);
     strcpy(pval->addr,node->val[node->count].addr);
//    strcpy(pval->usn,m.usn);
//   strcpy(pval->addr,m.addr);
	(*newNode)->link[0] = node->link[node->count];
	node->count--;
  }


  /* sets the value val in the node */
  int setValueInNode(index val,index *pval,
     btreeNode *node, btreeNode **child) {

	int pos;
	if (!node) {
		*pval = val;
		*child = NULL;
		return 1;
	}

	if (strcmp(val.iusn,node->val[1].iusn)<0) {
		pos = 0;
	} else {
		for (pos = node->count;
			((strcmp(val.iusn,node->val[pos].iusn)<0) && pos > 1); pos--);
		if (strcmp(val.iusn,node->val[pos].iusn)==0) {
			cout<<"\t\tDuplicates not allowed\n";
			q=0;
			return 0;
		}
	}
	if (setValueInNode(val, pval, node->link[pos], child)) {
		if (node->count < MAX) {
			addValToNode(*pval, pos, node, *child);
		} else {

	    //	strcpy(m.usn,val.usn);
	    //	strcpy(m.addr,val.addr);
			splitNode(*pval, pval,pos, node, *child, child);
			return 1;
		}
	}
	return 0;
  }

  /* insert val in B-Tree */
  void insertion(index val) {
	int flag;
	index i;
	btreeNode *child;
     //	 cout<<"start\n";
	flag = setValueInNode(val, &i, root, &child);
	if (flag)
		root = createNode(i, child);
  }


   /* copy successor for the value to be deleted */
  void copySuccessor(btreeNode *myNode, int pos) {
	btreeNode *dummy;
   //	cout<<"h1\n";
	dummy = myNode->link[pos];

	for (;dummy->link[0] != NULL;)
		dummy = dummy->link[0];
		 myNode->val[pos] = dummy->val[1];
	strcpy(myNode->val[pos].iusn,dummy->val[1].iusn);
	strcpy(myNode->val[pos].addr,dummy->val[1].addr);

  }
   /* removes the value from the given node and rearrange values */
  void removeVal(btreeNode *myNode, int pos) {
	int i = pos + 1;
   //	cout<<"h2\n";
  //	cout<<i<<endl;
	while (i <= myNode->count) {
		myNode->val[i - 1] = myNode->val[i];
	strcpy(myNode->val[i-1].iusn,myNode->val[i].iusn);
	strcpy(myNode->val[i-1].addr,myNode->val[i].addr);
		myNode->link[i - 1] = myNode->link[i];
		i++;
	}
	myNode->count--;
  }
  /* shifts value from parent to right child */
  void doRightShift(btreeNode *myNode, int pos) {
	btreeNode *x = myNode->link[pos];
	int j = x->count;
  //	cout<<"h3\n";
	while (j > 0) {
		x->val[j + 1] = x->val[j];
		strcpy(x->val[j+1].iusn,x->val[j].iusn);
		strcpy(x->val[j+1].addr,x->val[j].addr);
		x->link[j + 1] = x->link[j];
		j--;
	}
	x->val[1] = myNode->val[pos];
	strcpy(x->val[1].iusn,myNode->val[pos].iusn);
	strcpy(x->val[1].addr,myNode->val[pos].addr);
	x->link[1] = x->link[0];
	x->count++;
   //	cout<<myNode->link[pos-1]<<endl<<myNode->link[pos]<<endl;
	x = myNode->link[pos - 1];
	myNode->val[pos] = x->val[x->count];
	strcpy(myNode->val[pos].iusn,x->val[x->count].iusn);
	strcpy(myNode->val[pos].addr,x->val[x->count].addr);
     //	myNode->link[pos] = x->link[x->count];
    //	cout<<x->link[x->count-1]<<endl<<x->link[x->count]<<endl;
	x->link[x->count-1]=x->link[x->count];
  //	cout<<x->link[x->count-1]<<endl;
	x->count--;

	return;
  }
  /* shifts value from parent to left child */
  void doLeftShift(btreeNode *myNode, int pos) {
	int j = 1;
 //	cout<<"h4\n";
	btreeNode *x = myNode->link[pos - 1];

	x->count++;
	x->val[x->count] = myNode->val[pos];
	strcpy(x->val[x->count].iusn,myNode->val[pos].iusn);
	strcpy(x->val[x->count].addr,myNode->val[pos].addr);
	x->link[x->count] = myNode->link[pos]->link[0];

	x = myNode->link[pos];
	myNode->val[pos] = x->val[1];
	strcpy(myNode->val[pos].iusn,x->val[1].iusn);
	strcpy(myNode->val[pos].addr,x->val[1].addr);
	x->link[0] = x->link[1];
	x->count--;

	while (j <= x->count) {
		x->val[j] = x->val[j + 1];
		strcpy(x->val[j].iusn,x->val[j + 1].iusn);
		strcpy(x->val[j].addr,x->val[j + 1].addr);
		x->link[j] = x->link[j + 1];
		j++;
	}
	return;
  }

  /* merge nodes */
  void mergeNodes(btreeNode *myNode, int pos) {
	int j = 1;
 //	cout<<"h5\n";
	btreeNode *x1 = myNode->link[pos], *x2 = myNode->link[pos - 1];

	x2->count++;
	x2->val[x2->count] = myNode->val[pos];
	strcpy(x2->val[x2->count].iusn,myNode->val[pos].iusn);
	strcpy(x2->val[x2->count].addr,myNode->val[pos].addr);
	x2->link[x2->count] = x2->link[x2->count-1];
	//myNode->link[0];
   //	 cout<<j<<endl;
	while (j <= x1->count) {
		x2->count++;
		x2->val[x2->count] = x1->val[j];
		strcpy(x2->val[x2->count].iusn,x1->val[j].iusn);
		strcpy(x2->val[x2->count].addr,x1->val[j].addr);
		x2->link[x2->count] =x1->link[j];
		//x1->link[j];
		j++;
	}
  //	for(int c=0;c<=x2->count;c++)
  //	cout<<x2->link[c]<<endl;

	j = pos;
     //	cout<<j<<endl;
	while (j < myNode->count) {
		myNode->val[j] = myNode->val[j + 1];
		strcpy(myNode->val[j].iusn,myNode->val[j + 1].iusn);
		strcpy(myNode->val[j].addr,myNode->val[j + 1].addr);
		myNode->link[j] = myNode->link[j + 1];
		j++;
	}
	myNode->count--;
  //	x2->link[x2->count+1] =NULL;
	free(x1);

  }

  /* adjusts the given node */
  void adjustNode(btreeNode *myNode, int pos) {
//  cout<<"h6\n";
	if (!pos) {
		if (myNode->link[1]->count > MIN) {
			doLeftShift(myNode, 1);
		} else {
	//	cout<<"yo"<<endl;
			mergeNodes(myNode, 1);
		}
	} else {
		if (myNode->count != pos) {
			if(myNode->link[pos - 1]->count > MIN) {
				doRightShift(myNode, pos);
			} else {
				if (myNode->link[pos + 1]->count > MIN) {
					doLeftShift(myNode, pos + 1);
				} else {
	//			cout<<"yup"<<endl;
					mergeNodes(myNode, pos);
				}
			}
		} else {
			if (myNode->link[pos - 1]->count > MIN)
				doRightShift(myNode, pos);
			else
				mergeNodes(myNode, pos);
		}
	}
  }

  /* delete val from the node */
  int delValFromNode(index val, btreeNode *myNode) {
	int pos, flag = 0;
   //	cout<<"h7\n";
	if (myNode) {
    //	cout<<myNode->val[1].iusn<<endl;
		if (strcmp(val.iusn,myNode->val[1].iusn)<0) {

			pos = 0;
			flag = 0;
		} else {
     //		cout<<myNode->count<<endl;
			for (pos = myNode->count;
				(strcmp(val.iusn,myNode->val[pos].iusn)<0 && pos > 1); pos--);
     //			 cout<<pos<<endl;
			 if (strcmp(val.iusn,myNode->val[pos].iusn)==0) {
				flag = 1;
			} else {
				flag = 0;
			}
		}
      //		cout<<flag<<endl;
		if(flag==1 && z==1)
		{
		 sfile.seekp(atoi(myNode->val[pos].addr),ios::beg);
		 sfile.put('$');
		 x=1;
		}
	//	student s1;
	//	strcpy(s1.usn,myNode->val[pos].iusn);
		if (flag) {
			if (myNode->link[pos - 1]) {
				copySuccessor(myNode, pos);
				z=0;
				flag = delValFromNode(myNode->val[pos], myNode->link[pos]);
				if (flag == 0) {
				x=0;
					cout<<"\t\tRecord not found\n";
				}
			} else {
				removeVal(myNode, pos);
			}
		} else {
	     //cout<<(myNode->link[pos])->iusn<<endl;
			flag = delValFromNode(val, myNode->link[pos]);
		}
		if (myNode->link[pos]) {
			if (myNode->link[pos]->count < MIN)
				adjustNode(myNode, pos);
		}
	}
	return flag;
  }

  /* delete val from B-tree */
  void deletion(index val, btreeNode *myNode) {
	btreeNode *tmp;
	z=1;
	if (!delValFromNode(val, myNode)) {
	x=0;
		cout<<"\t\tRecord not found\n";
		return;
	} else {
		if (myNode->count == 0) {
			tmp = myNode;
			myNode = myNode->link[0];
			free(tmp);
		}
	}
	root = myNode;
	return;
  }

  void student::unpack()
  {
  char dummy[75];
char ch[5];
sfile.getline(usn,15,'|');
sfile.getline(ch,5,'|');
sub1ia=atoi(ch);
sfile.getline(ch,5,'|');
sub2ia=atoi(ch);
sfile.getline(ch,5,'|');
sub3ia=atoi(ch);
sfile.getline(ch,5,'|');
sub4ia=atoi(ch);
sfile.getline(ch,5,'|');
sub5ia=atoi(ch);
sfile.getline(ch,5,'|');
sub6ia=atoi(ch);
sfile.getline(ch,5,'|');
lab1ia=atoi(ch);
sfile.getline(ch,5,'|');
lab2ia=atoi(ch);
sfile.getline(ch,5,'|');
sub1ea=atoi(ch);
sfile.getline(ch,5,'|');
sub2ea=atoi(ch);
sfile.getline(ch,5,'|');
sub3ea=atoi(ch);
sfile.getline(ch,5,'|');
sub4ea=atoi(ch);
sfile.getline(ch,5,'|');
sub5ea=atoi(ch);
sfile.getline(ch,5,'|');
sub6ea=atoi(ch);
sfile.getline(ch,5,'|');
lab1ea=atoi(ch);
sfile.getline(ch,5,'|');
lab2ea=atoi(ch);
sfile.getline(ch,5,'|');
sgpa=atof(ch);
sfile.getline(dummy,75,'\n');
  }
  /* search val in B-Tree */
  void searching(index val, int *pos, btreeNode *myNode) {
	if (!myNode) {
		return;
	}

	if (strcmp(val.iusn,myNode->val[1].iusn)<0) {
		*pos = 0;
	} else {
		for (*pos = myNode->count;
			(strcmp(val.iusn,myNode->val[*pos].iusn)<0 && *pos > 1); (*pos)--);
		if (strcmp(val.iusn,myNode->val[*pos].iusn)==0) {
		      h=1;
			sfile.seekg(atoi(myNode->val[*pos].addr),ios::beg);
	      //		cout<<atoi(myNode->val[*pos].addr)<<endl;
			s.unpack();
  if(y==1)
  {
  cout<<"\t\tRecord found\n";
  cout<<endl<<setw(12)<<"USN"<<setw(8)<<"CS11"<<setw(8)<<"CS12"
  <<setw(8)<<"CS13"<<setw(8)<<"CS14"<<setw(8)<<"CS15"<<setw(8)<<"CS16"
  <<setw(8)<<"CSL1"<<setw(8)<<"CSL2"<<setw(4)<<"SGPA"<<endl;
  cout<<"            "<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"
  <<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"
  <<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"
  <<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"
  <<setw(4)<<"EXT"<<endl;
  } cout<<setw(12)<<s.usn<<setw(4)<<s.sub1ia<<setw(4)<<s.sub2ia<<setw(4)
  <<s.sub3ia<<setw(4)<<s.sub4ia<<setw(4)<<s.sub5ia<<setw(4)<<s.sub6ia
  <<setw(4)<<s.lab1ia<<setw(4)<<s.lab2ia<<setw(4)<<s.sub1ea<<setw(4)
  <<s.sub2ea<<setw(4)<<s.sub3ea<<setw(4)<<s.sub4ea<<setw(4)<<s.sub5ea
  <<setw(4)<<s.sub6ea<<setw(4)<<s.lab1ea<<setw(4)<<s.lab2ea<<setw(4)
  <<s.sgpa<<endl;
  return; }}
  searching(val, pos, myNode->link[*pos]);
  return;
  }

  /* B-Tree Traversal */
  void traversal(btreeNode *myNode)
  {
	int i;
	index x;
	if (myNode) {
	for (i = 0; i < myNode->count; i++) {
	traversal(myNode->link[i]);
	strcpy(x.iusn,myNode->val[i+1].iusn);
	searching(x,&ch,root);
	}
	traversal(myNode->link[i]);
	}
  }

  void traversal1(btreeNode *myNode)
  {
	int i;
	if (myNode) {
	for (i = 0; i < myNode->count; i++) {
	traversal1(myNode->link[i]);
	ifile<<myNode->val[i + 1].iusn<<"|"<<myNode->val[i + 1].addr<<endl;
	}
	traversal1(myNode->link[i]);
	}
  }

void unsortdisp()
{
while(1)
{
s.unpack();
if(sfile.eof()) break;
if(s.usn[0]!='$')
  cout<<setw(12)<<s.usn<<setw(4)<<s.sub1ia<<setw(4)<<s.sub2ia<<setw(4)
  <<s.sub3ia<<setw(4)<<s.sub4ia<<setw(4)<<s.sub5ia<<setw(4)<<s.sub6ia
  <<setw(4)<<s.lab1ia<<setw(4)<<s.lab2ia<<setw(4)<<s.sub1ea<<setw(4)
  <<s.sub2ea<<setw(4)<<s.sub3ea<<setw(4)<<s.sub4ea<<setw(4)<<s.sub5ea
  <<setw(4)<<s.sub6ea<<setw(4)<<s.lab1ea<<setw(4)<<s.lab2ea<<setw(4)
  <<s.sgpa<<endl;

}
}

void traversal2(btreeNode *p)
{

  int i;
  static int j=1;
  cout<< "\n  Level-" << j <<":";
  bfile<< "\n  Level-" << j++ <<":";
  cout<<" NODE "<<d;
  bfile<<" NODE "<<d++;
  for(i=0;i<p->count;i++)
  {
    cout << " ENTRY "<<i+1<<"  " << p->val[i+1].iusn;
    bfile << " ENTRY "<<i+1<<"  " << p->val[i+1].iusn;
    }

  cout <<"\n------------------------------------------------------------------------\n";
  for(i=0;i<=p->count;i++)
    if(p->link[i])
    {
     traversal2(p->link[i]); }
  j--;
}

void initial()
{
//int u=0;
&ifile=&ifile1;
root=rootx;
strcpy(indfile,indfile1);
&sfile=&sfile1;
strcpy(datafile,datafile1);
p=0;
ifile.open(indfile,ios::in);
sfile.open(datafile,ios::in);
if(!ifile)
{
//cout<<"no records\n";
//u=1;
}
index x;
while(1)
{
ifile.getline(x.iusn,15,'|');
ifile.getline(x.addr,5,'\n');
if(x.iusn[0]==' '||x.iusn[0]=='\0')
break;
//if(ifile.eof()) break;
//cout<<x.iusn<<endl<<x.addr<<endl;
i1++;
//ad=atoi(ch);
//ad1[j++]=ad;
//cout<<ad<<endl;

insertion(x);
if(ifile.eof()) break;
}
//traversal(root);
//if(u==0)
rootx=root;
//cout<<rootx<<endl;
//else
//rootx=NULL;
//traversal(rootx);
//root1=*root;
ifile.close();
sfile.close();
//j=0;
//u=0;
&ifile=&ifile2;
root=rooty;
strcpy(datafile,datafile2);
&sfile=&sfile2;
strcpy(indfile,indfile2);
sfile.open(datafile,ios::in);
ifile.open(indfile,ios::in);
if(!ifile)
{
//cout<<"no records\n";
//u=1;
}
while(1)
{
ifile.getline(x.iusn,15,'|');
ifile.getline(x.addr,5,'\n');
if(x.iusn[0]==' '||x.iusn[0]=='\0')
break;

//cout<<x.usn<<endl<<ch<<endl;
i2++;
//ad=atoi(ch);
//ad1[j++]=ad;
insertion(x);
if(ifile.eof()) break;
}
//traversal(root);
//if(u==0)
rooty=root;
//cout<<rooty<<endl;
//else
//rooty=NULL;
ifile.close();
sfile.close();
}

void searching1(index val, int *pos, btreeNode *myNode) {
	if (!myNode) {
		return;
	}

	if (strcmp(val.iusn,myNode->val[1].iusn)<0) {
		*pos = 0;
	} else {
		for (*pos = myNode->count;
			(strcmp(val.iusn,myNode->val[*pos].iusn)<0 && *pos > 1); (*pos)--);
		if (strcmp(val.iusn,myNode->val[*pos].iusn)==0) {
		      f=1;
			sfile.seekg(atoi(myNode->val[*pos].addr),ios::beg);
			 s.unpack();


			cout<<"SGPA = "<<s.sgpa<<endl;
			cgpa+=s.sgpa;
			return;
		}
	}
	searching1(val, pos, myNode->link[*pos]);
	return;
  }

   void student::upperto()
   {
   char str1[15],str2[15];
   int j;
   do
   {
cout<<"\t\tEnter usn:";
cin>>str1;
}while(strlen(str1)!=10 || (isdigit(str1[0])==0 || isalpha(str1[1])==0 || isalpha(str1[2])==0 || isdigit(str1[3])==0 || isdigit(str1[4])==0 || isalpha(str1[5])==0 || isalpha(str1[6])==0 || isdigit(str1[7])==0 || isdigit(str1[8])==0 || isdigit(str1[9])==0) );

for(j=0;str1[j]!='\0';j++)
str2[j]=toupper(str1[j]);
str2[j]='\0';
strcpy(usn,str2);
   }
  int main() {
	int val,sem,o;
		char str1[20],str2[20],a[2];
	int j;
	index c;
	clrscr();
	cout<<root<<endl<<rootx<<endl<<rooty<<endl;
      initial();
    //  getch();
	while (1) {
	clrscr();

    textcolor(YELLOW);
     textbackground(GREEN);
     cout<<setiosflags(ios::left);
     cout<<" \n\n\n\t\t\t CUMULATIVE GRADE POINT AVERAGE (CGPA) "<<"\n";
     cout<<"****************************************************************************\n\n\n";
		cout<<"\t\t1. Insertion\n\t\t2. Display Records \n\t\t3. Display B TREE\n\t\t4. Deletion\n\t\t5. Search\n\t\t6. Modify \n\t\t7. CGPA \n\t\t8. Exit\n\t\tEnter your choice:";
		cin>>ch;
		switch (ch) {
		case 3:
			clrscr();
			textcolor(BLACK);
			textbackground(WHITE);
		  //	d=1;
			cout<<setiosflags(ios::left);
				cout<<"\n\n\n";
		   //    d=0;
		      cout<<"\t\tSem1 B TREE:\n\n";
			 r=i1;
				if(r!=0)
			{
		    //	 cout<<"\n\n\n"<<setw(12)<<"USN"<<setw(8)<<"CS11"<<setw(8)<<"CS12"<<setw(8)<<"CS13"<<setw(8)<<"CS14"<<setw(8)<<"CS15"<<setw(8)<<"CS16"<<setw(8)<<"CSL1"<<setw(8)<<"CSL2"<<setw(4)<<"SGPA"<<endl;
		    //	 cout<<"            "<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<endl;
				&bfile=&bfile1;
				root=rootx;
		      //		for (i = 0; i < root->count; i++) {
			 //	cout<<root->val[i + 1].iusn<<"\t";
		       //	}
			   //	for(int c=0;c<=root->count;c++)
			    //	cout<<root->link[c]<<endl;
				strcpy(treefile,treefile1);
				opener(bfile,treefile,ios::out);
				 d=1;
				traversal2(root);
				bfile.close();
				}
				else
				{
				cout<<"\t\tNo records\n\n";
				}

		     cout<<setiosflags(ios::left);
				cout<<"\t\tSem2 B TREE:\n\n";
				 r=i2;
					if(r!=0)
			{
			 //	 cout<<"\n\n\n"<<setw(12)<<"USN"<<setw(8)<<"CS21"<<setw(8)<<"CS22"<<setw(8)<<"CS23"<<setw(8)<<"CS24"<<setw(8)<<"CS25"<<setw(8)<<"CS26"<<setw(8)<<"CSL1"<<setw(8)<<"CSL2"<<setw(4)<<"SGPA"<<endl;
		  //	 cout<<"            "<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<endl;
				&bfile=&bfile2;
				root=rooty;
			strcpy(treefile,treefile2);
			opener(bfile,treefile,ios::out);
				  d=1;
				traversal2(root);
				bfile.close();
				}
				else
				{
				cout<<"\t\tNo records\n\n";

				}
		break;
			case 1:
     textcolor(BLUE);
     textbackground(YELLOW);
		    //	d=0;
			clrscr();
			p=1,o=1,q=1;
			n=0;
			cout<<"\n\n\n";
while(o)
{
cout<<"\t\tEnter sem:";
cin>>a;
if(isalpha(a[0])!=0 || strlen(a)>1)
o=1;
else
{
sem=atoi(a);
if(sem>2)
o=1;
else o=0;
}
}
				s.upperto();
				strcpy(c.iusn,s.usn);
				if(sem==1)
				{
				i1++;
				&sfile=&sfile1;
				root=rootx;
				strcpy(datafile,datafile1);
				}
				else
				{
				i2++;
				&sfile=&sfile2;
				root=rooty;
				strcpy(datafile,datafile2);
				}
				opener(sfile,datafile,ios::app);
				sfile.seekg(0,ios::end);
				ad=sfile.tellg();
				itoa(ad,c.addr,10);
			   //	cout<<root->val[1].iusn<<endl;

			   //	cout<<root->count<<endl;
				insertion(c);
			   //	cout<<root->val[1].iusn<<endl;
			      //	cout<<root->val[1].iusn<<endl;
			    //	cout<<root->count<<endl;
			    if(q==1){
				if(sem==1)
				{
				rootx=root;
				&ifile=&ifile1;
				strcpy(indfile,indfile1);
				opener(ifile,indfile,ios::out);
				traversal1(root);
				ifile.close();
				&ifile=&ifile2;
				strcpy(indfile,indfile2);
				opener(ifile,indfile,ios::out);
				root=rooty;
				traversal1(root);
				rooty=root;
				ifile.close();
				}
				else
				{
				rooty=root;
				&ifile=&ifile2;
				strcpy(indfile,indfile2);
				opener(ifile,indfile,ios::out);
				traversal1(root);
				ifile.close();
				&ifile=&ifile1;
				strcpy(indfile,indfile1);
				opener(ifile,indfile,ios::out);
				root=rootx;
				traversal1(root);
				rootx=root;
				ifile.close();
				}     }
				sfile.close();
				break;
			case 4: 	clrscr();
				cout<<"\n\n\n";
			textcolor(RED);
     textbackground(BLACK);
			  o=1;
			 cout<<"\n\n\n";
while(o)
{
cout<<"\t\tEnter sem:";
cin>>a;
if(isalpha(a[0])!=0 || strlen(a)>1)
o=1;
else
{
sem=atoi(a);
if(sem>2)
o=1;
else o=0;
}
}
			  //	cout<<rootx<<endl<<rooty<<endl;
				if(sem==1)
				{
				&sfile=&sfile1;
				root=rootx;
				strcpy(datafile,datafile1);
				r=i1;
				}
				else
				{
				r=i2;
				&sfile=&sfile2;
				root=rooty;
				strcpy(datafile,datafile2);
				}
		//	   cout<<root<<endl;
				if(r==0)
				cout<<"\t\tNo records to delete\n";
				else
				{
				cout<<"\t\tEnter element to delete:\n";
				 s.upperto();
				 strcpy(c.iusn,s.usn);
				opener(sfile,datafile,ios::in|ios::out);
			   //	cout<<root->val[1].iusn<<endl;
			   //	cout<<root->val[2].iusn<<endl;
			    //		cout<<root->count<<endl;
			   //	getch();
				deletion(c, root);
			    //	getch();
			    //	cout<<root->val[1].iusn<<endl;
			 //	cout<<root->val[2].iusn<<endl;
			   //		cout<<root->count<<endl;
			 //	getch();
		      //	  cout<<root<<endl;
				sfile.close();
			    //	opener(sfile,datafile,ios::in);
			    //	traversal(root);
			    //	traversal1(root);
				if(x==1)
				{
				cout<<"\t\tRecord deleted\n";
				r--;
				if(sem==1)
				{
				i1=r;
				rootx=root;
			   //	cout<<rootx<<endl;
				&ifile=&ifile1;
				strcpy(indfile,indfile1);
				opener(ifile,indfile,ios::out);
			     //	root=rootx;
			//     cout<<"hi\n";
				traversal1(root);
			  //	cout<<"hello\n";
				rootx=root;
				ifile.close();
				&ifile=&ifile2;
				strcpy(indfile,indfile2);
				opener(ifile,indfile,ios::out);
			      //	cout<<rooty<<endl;
				root=rooty;
			     //	cout<<root<<endl;
				traversal1(root);
		       //		rooty=root;
				ifile.close();
				}
				else
				{
				i2=r;

				&ifile=&ifile2;
				strcpy(indfile,indfile2);
				opener(ifile,indfile,ios::out);
			    //	root=rooty;
				traversal1(root);
				rooty=root;
				ifile.close();
				&ifile=&ifile1;
				strcpy(indfile,indfile1);
				opener(ifile,indfile,ios::out);
				root=rootx;
				traversal1(root);
		      //		rootx=root;
				ifile.close();
				}

				}
				}
				break;
			case 5:     y=1;   h=0;
					clrscr();
					textcolor(GREEN);
     textbackground(BLACK);
					//  initial();
				 o=1;
					cout<<"\n\n\n";

while(o)
{
cout<<"\t\tEnter sem:";
cin>>a;
if(isalpha(a[0])!=0 || strlen(a)>1)
o=1;
else
{
sem=atoi(a);
if(sem>2)
o=1;
else o=0;
}
}

				if(sem==1)
				{
				r=i1;
				&sfile=&sfile1;
				root=rootx;
				strcpy(datafile,datafile1);
				}
				else
				{
				r=i2;
				&sfile=&sfile2;
				root=rooty;
				strcpy(datafile,datafile2);
				}
				if(r!=0)
				{
				 cout<<"\t\tEnter the element to search:\n";
				s.upperto();
				strcpy(c.iusn,s.usn);
				opener(sfile,datafile,ios::in);
				searching(c, &ch, root);
				sfile.close();
				if(h==0)
				cout<<"\t\tRecord not found\n";


				}
				else cout<<"\t\tNo records in file\n";
				break;
			case 9:   y=0;
			clrscr();
			textcolor(BLACK);
     textbackground(WHITE);
		  //	d=1;
			cout<<setiosflags(ios::left);
				cout<<"\n\n\n";

		      cout<<"\t\tSem1:";
			 r=i1;
				if(r!=0)
			{

			 cout<<"\n\n\n"<<setw(12)<<"USN"<<setw(8)<<"CS11"<<setw(8)<<"CS12"<<setw(8)<<"CS13"<<setw(8)<<"CS14"<<setw(8)<<"CS15"<<setw(8)<<"CS16"<<setw(8)<<"CSL1"<<setw(8)<<"CSL2"<<setw(4)<<"SGPA"<<endl;
			 cout<<"            "<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<endl;
				&sfile=&sfile1;
				root=rootx;
			   //	for(int c=0;c<=root->count;c++)
			    //	cout<<root->link[c]<<endl;
				strcpy(datafile,datafile1);
				opener(sfile,datafile,ios::in);
				traversal(root);
				sfile.close();
				}
				else
				{
				cout<<"\t\tNo records\n\n";
				}

		    cout<<setiosflags(ios::left);
				cout<<"\t\tSem2:";
				 r=i2;
					if(r!=0)
			{
				 cout<<"\n\n\n"<<setw(12)<<"USN"<<setw(8)<<"CS21"<<setw(8)<<"CS22"<<setw(8)<<"CS23"<<setw(8)<<"CS24"<<setw(8)<<"CS25"<<setw(8)<<"CS26"<<setw(8)<<"CSL1"<<setw(8)<<"CSL2"<<setw(4)<<"SGPA"<<endl;
			 cout<<"            "<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<endl;
				&sfile=&sfile2;
				root=rooty;
				strcpy(datafile,datafile2);
				opener(sfile,datafile,ios::in);
				traversal(root);
				sfile.close();
				}
				else
				{
				cout<<"\t\tNo records\n\n";

				}
				break;

			case 2:   y=0;
			clrscr();
			textcolor(BLACK);
     textbackground(WHITE);
		  //	d=1;
			cout<<setiosflags(ios::left);
				cout<<"\n\n\n";

		      cout<<"\t\tSem1:";
			 r=i1;
				if(r!=0)
			{

			 cout<<"\n\n\n"<<setw(12)<<"USN"<<setw(8)<<"CS11"<<setw(8)<<"CS12"<<setw(8)<<"CS13"<<setw(8)<<"CS14"<<setw(8)<<"CS15"<<setw(8)<<"CS16"<<setw(8)<<"CSL1"<<setw(8)<<"CSL2"<<setw(4)<<"SGPA"<<endl;
			 cout<<"            "<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<endl;
				&sfile=&sfile1;
			      //	root=rootx;
			   //	for(int c=0;c<=root->count;c++)
			    //	cout<<root->link[c]<<endl;
				strcpy(datafile,datafile1);
				opener(sfile,datafile,ios::in);
				unsortdisp();
			      //	traversal(root);
				sfile.close();
				}
				else
				{
				cout<<"\t\tNo records\n\n";
				}

		     cout<<setiosflags(ios::left);
				cout<<"\t\tSem2:";
				 r=i2;
					if(r!=0)
			{
				 cout<<"\n\n\n"<<setw(12)<<"USN"<<setw(8)<<"CS21"<<setw(8)<<"CS22"<<setw(8)<<"CS23"<<setw(8)<<"CS24"<<setw(8)<<"CS25"<<setw(8)<<"CS26"<<setw(8)<<"CSL1"<<setw(8)<<"CSL2"<<setw(4)<<"SGPA"<<endl;
			 cout<<"            "<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<setw(4)<<"IA"<<setw(4)<<"EXT"<<endl;
				&sfile=&sfile2;
			   //	root=rooty;
				strcpy(datafile,datafile2);
				opener(sfile,datafile,ios::in);
				unsortdisp();
			   //	traversal(root);
				sfile.close();
				}
				else
				{
				cout<<"\t\tNo records\n\n";

				}
				break;

			case 6:clrscr(); //   initial();
		       textcolor(BLACK);
     textbackground(YELLOW);	  o=1;
				    cout<<"\n";
while(o)
{
cout<<"\t\tEnter sem:";
cin>>a;
if(isalpha(a[0])!=0 || strlen(a)>1)
o=1;
else
{
sem=atoi(a);
if(sem>2)
o=1;
else o=0;
}
}
				if(sem==1)
				{
				&sfile=&sfile1;
				root=rootx;
				strcpy(datafile,datafile1);
				r=i1;
				}
				else
				{
				r=i2;
				&sfile=&sfile2;
				root=rooty;
				strcpy(datafile,datafile2);
				}
				if(r==0)
				cout<<"\t\tNo records to modify\n";
				else
				{
				cout<<"\t\tEnter element to delete:\n";
				 s.upperto();
				 strcpy(c.iusn,s.usn);
				opener(sfile,datafile,ios::in|ios::out);
				deletion(c, root);
				sfile.close();
				if(x==1)
				{
				cout<<"\t\tRecord deleted\n";
				r--;
				if(sem==1)
				{
				rootx=root;
				}
				else
				{
				rooty=root;
				}
				&ifile=&ifile1;
				strcpy(indfile,indfile1);
				opener(ifile,indfile,ios::out);
				root=rootx;
				traversal1(root);
				ifile.close();
				&ifile=&ifile2;
				strcpy(indfile,indfile2);
				opener(ifile,indfile,ios::out);
				root=rooty;
				traversal1(root);
				ifile.close();
				if(sem==1)
				{
			//	rootx=root;
				i1=r;
				&sfile=&sfile1;
				root=rootx;
				strcpy(datafile,datafile1);

				}
				else
				{
			//	rooty=root;
				i2=r;
				&sfile=&sfile2;
				root=rooty;
				strcpy(datafile,datafile2);
				}
				n=0;
				opener(sfile,datafile,ios::app);
				do
				{
				sfile.seekg(0,ios::end);
				ad=sfile.tellg();
				itoa(ad,c.addr,10);
				s.upperto();
				strcpy(c.iusn,s.usn);
				p=1;
				q=1;
				insertion(c);
				}while(q==0);
				if(sem==1)
				{
				rootx=root;
				i1++;
				}
				else
				{
				rooty=root;
				i2++;
				}
					}
				}
			     //	sfile.close();
					&ifile=&ifile1;
				strcpy(indfile,indfile1);
				opener(ifile,indfile,ios::out);
				root=rootx;
				traversal1(root);
				ifile.close();
				&ifile=&ifile2;
				strcpy(indfile,indfile2);
				opener(ifile,indfile,ios::out);
				root=rooty;
				traversal1(root);
			   ifile.close();

				break;
			case 7:         	clrscr();
			cout<<"\n\n\n";
			cgpa=0;   textcolor(MAGENTA);
     textbackground(GREEN);
			if(i1!=0  || i2!=0)
s.upperto();
strcpy(c.iusn,s.usn);
&sfile=&sfile1;
strcpy(datafile,datafile1);
root=rootx;
	if(i1!=0 ||  i2!=0)
cout<<"\t\tSem1 ";
f=0;
r=i1;
if(r!=0)
{
opener(sfile,datafile,ios::in);
searching1(c, &ch, root);
sfile.close();
}
f1=f;
if(f1==0)
	if(i1!=0 ||  i2!=0)
cout<<"SGPA not found\n";

&sfile=&sfile2;
strcpy(datafile,datafile2);
root=rooty;
	r=i2;
		if(i1!=0 ||  i2!=0)
cout<<"\t\tSem2 ";
f=0;
if(r!=0)
{
opener(sfile,datafile,ios::in);
searching1(c, &ch, root);
sfile.close();
}
f2=f;
if(f2==0)
	if(i1!=0 ||  i2!=0)
cout<<"SGPA not found\n";
if((f1==0)||(f2==0)) cgpa*=2;
cgpa/=2;
if(cgpa!=0)
cout<<"\t\tCurrent CGPA = "<<setprecision(2)<<cgpa;
else
cout<<"\t\tNo entries in both files"<<endl;
			break;
			case 8:

				exit(0);
			default:
				cout<<"U have entered wrong option!!\n";
				break;
		}
		cout<<"\n";
	    cout<<"\t\tPress any key to go to Menu\n";
		getch();
		sfile.close();
		ifile.close();
	}
  }
