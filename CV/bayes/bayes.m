RGB=imread('joe.jpg');
R=RGB(:,:,1);
G=RGB(:,:,2);
B=RGB(:,:,3);
% imhist(R);
% figure,imhist(G)
% figure,imhist(B);

[m,n]=size(RGB);
m
n
m1=randi(m)
n1=randi(n)
value=RGB(m1,n1,1)