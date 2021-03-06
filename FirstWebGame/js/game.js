// Create the canvas
var canvas = document.createElement("canvas");
var ctx = canvas.getContext("2d");
canvas.width = 512;
canvas.height = 480;
document.body.appendChild(canvas);

// Background image
var bgReady = false;
var bgImage = new Image();
bgImage.onload = function () {
    bgReady = true;
};
bgImage.src = "images/background.png";

// Hero image
var heroReady = false;
var heroImage = new Image();
heroImage.onload = function () {
    heroReady = true;
};
heroImage.src = "images/hero.png";

// Monster image
var monsterReady = false;
var monsterImage = new Image();
monsterImage.onload = function () {
    monsterReady = true;
};
monsterImage.src = "images/monster.png";

// Game objects
var hero = {
    speed: 256 // movement in pixels per second
};
var monster = {};
var monstersCaught = 0;

// Handle keyboard controls
var keysDown = {};

addEventListener("keydown", function (e) {
    keysDown[e.keyCode] = true;
}, false);

addEventListener("keyup", function (e) {
    delete keysDown[e.keyCode];
}, false);

// Reset the game when the player catches a monster
var reset = function () {
    hero.x = canvas.width / 2;
    hero.y = canvas.height / 2;

    // Throw the monster somewhere on the screen randomly
    monster.x = 32 + (Math.random() * (canvas.width - 64));
    monster.y = 32 + (Math.random() * (canvas.height - 64));
};

// Update game objects
var update = function (modifier) {
    if (38 in keysDown) { // Player holding up
        hero.y -= hero.speed * modifier;
        // 在背景图片的障碍物内
        if (hero.y < 32) {
            hero.y = 32;
        }
    }
    if (40 in keysDown) { // Player holding down
        hero.y += hero.speed * modifier;
        if (hero.y >= 416) {
            hero.y = 416;
        }
    }
    if (37 in keysDown) { // Player holding left
        hero.x -= hero.speed * modifier;
        if (hero.x < 32) {
            hero.x = 32;
        }
    }
    if (39 in keysDown) { // Player holding right
        hero.x += hero.speed * modifier;
        if (hero.x > 448) {
            hero.x = 448;
        }
    }

    // Are they touching?
    if (
        hero.x <= (monster.x + 32)
        && monster.x <= (hero.x + 32)
        && hero.y <= (monster.y + 32)
        && monster.y <= (hero.y + 32)
    ) {
        ++monstersCaught;
        reset();
    }
};

// Draw everything
var render = function () {
    if (bgReady) {
        ctx.drawImage(bgImage, 0, 0);
    }

    if (heroReady) {
        ctx.drawImage(heroImage, hero.x, hero.y);
    }

    if (monsterReady) {
        ctx.drawImage(monsterImage, monster.x, monster.y);
    }

    // Score
    ctx.fillStyle = "rgb(250, 250, 250)";
    ctx.font = "24px Helvetica";
    ctx.textAlign = "left";
    ctx.textBaseline = "top";
    ctx.fillText("Goblins caught: " + monstersCaught, 32, 32);
};

// The main game loop
var main = function () {
    var now = Date.now();
    var delta = now - then;

    // 这个参数是modifier，是一个影响移动速率的因子
    // 可以假设一下如果没有这个东西，那么如果render()和requestAnimationFrame(main)
    // 这两个方法执行很快，则update会更加频繁的调用，即单位时间内它调用
    // 的次数更多，所以单位时间内实际移动的速度要更大
    // 而有了这个因子，那么假设render执行很慢，则下一次的delta将会
    // 更大从而令移动的距离更远，而如果render执行的很快，那么下一次
    // delta则会较小，从而令下一次移动的距离更短
    // 达到单位时间内的移动速度不管运行速度如何都尽可能恒定
    update(delta / 1000);
    then = now;
    render();

    // Request to do this again ASAP
    requestAnimationFrame(main);
};

// Cross-browser support for requestAnimationFrame
var w = window;
requestAnimationFrame = w.requestAnimationFrame || w.webkitRequestAnimationFrame || w.msRequestAnimationFrame || w.mozRequestAnimationFrame;

// Let's play this game!
var then = Date.now();
reset();
main();