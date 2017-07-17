INSERT ALL
INTO Users (full_name,billing_Address,login,password) VALUES('fullName1','address1','login1','password')
INTO Users (full_name,billing_Address,login,password) VALUES('fullName2','address2','login2','password')
INTO Users (full_name,billing_Address,login,password) VALUES('fullName3','address3','login3','password')
INTO Users (full_name,billing_Address,login,password) VALUES('fullName3','address3','login4','password')
INTO Users (full_name,billing_Address,login,password) VALUES('fullName3','address3','login5','password')
INTO Users (full_name,billing_Address,login,password) VALUES('ADMIN','Moscow','admin','admin')
SELECT * FROM dual;

INSERT ALL
INTO Items (seller_Id,title,description,start_price,time_left,start_bidding_date,buy_it_now, bid_increment) VALUES('1','title1','description1','100','3600000',TO_DATE('01.01.2017', 'DD.MM.YYYY'),'0','1')
INTO Items (seller_Id,title,description,start_price,start_bidding_date,buy_it_now) VALUES('1','title2','description2','200',TO_DATE('01.01.2017', 'DD.MM.YYYY'),'1')
INTO Items (seller_Id,title,description,start_price,time_left, start_bidding_date,buy_it_now, bid_increment) VALUES('2','title3','description3','300','3600000',TO_DATE('01.01.2017', 'DD.MM.YYYY'),'0', '0.05')
INTO Items (seller_Id,title,description,start_price,start_bidding_date,buy_it_now) VALUES('2','title4','description4','400',TO_DATE('01.01.2017', 'DD.MM.YYYY'),'1')
SELECT * FROM dual;

INSERT ALL
INTO Bids (bidder_Id, item_Id, bid) VALUES ('3','1','200')
INTO Bids (bidder_Id, item_Id, bid) VALUES ('4','1','100')
INTO Bids (bidder_Id, item_Id, bid) VALUES ('3','2','300')
INTO Bids (bidder_Id, item_Id, bid) VALUES ('4','2','400')
SELECT * FROM dual;