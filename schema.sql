create table range
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    position ENUM('small_blind', 'big_blind', 'UTG', 'UTG1', 'MP', 'MP2', 'high_jack', 'cutoff', 'button') null,
    bet ENUM('open', 'call', 'fold', 'raise', 'three_bet', 'four_bet'),
    userId int not null,
    percentOfStartHands decimal(4,2) check (percentOfStartHands < 100 and percentOfStartHands < 0),
    foreign key (userId) references users(id)
);

create table start_hand (
    id int primary key auto_increment,
    holeOneValue int not null check (holeOneValue > 0 and holeOneValue < 13),
    holeTwoValue int not null check (holeOneValue > 0 and holeOneValue < 13),
    holeOneSuit enum('heart', 'diamond', 'spade', 'club'),
    holeTwoSuit enum('heart', 'diamond', 'spade', 'club'),
    rangeId int null,
    foreign key (rangeId) references range(id)
);

create table flop_data (
    startHandId int primary key,
    quads decimal(5,2), check (quads > 0 and quads < 100),
    fullHouse decimal(5,2), check (fullHouse > 0 and fullHouse < 100),
    flush decimal(5,2), check (flush > 0 and flush < 100),
    straight decimal(5,2), check (straight > 0 and straight < 100),
    threeKind decimal(5,2), check (threeKind > 0 and threeKind < 100),
    twoPair decimal(5,2), check (twoPair > 0 and twoPair < 100),
    overPair decimal(5,2), check (overPair > 0 and overPair < 100),
    middlePair decimal(5,2), check (middlePair > 0 and middlePair < 100),
    lowPair decimal(5,2), check (lowPair > 0 and lowPair < 100),
    aceHigh decimal(5,2), check (aceHigh > 0 and aceHigh < 100),
    noHand decimal(5,2), check (noHand > 0 and noHand < 100),
    flushDraw decimal(5,2), check (flushDraw > 0 and flushDraw < 100),
    nutFlushDraw decimal(5,2), check (nutFlushDraw > 0 and nutFlushDraw < 100),
    openEnder decimal(5,2), check (openEnder > 0 and openEnder < 100),
    iterations decimal(5,2), check (iterations > 0 and iterations < 100),
    last_updated datetime not null,
    foreign key (startHandId) references start_hand(id)
);

create table users (
    id int primary key auto_increment,
    email varchar(255) not null,
    first_name varchar(255) null,
    last_name varchar(255) null,
    created datetime not null,
    deleted datetime null
);

