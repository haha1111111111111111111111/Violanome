<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Violanome</title>
    <style>
        :root {
            --bg-color: #120c08;
            --wood-body: linear-gradient(180deg, #5a2e12, #2d1405);
            --wood-border: #7e421c;
            --accent-gold: #e5b35c;
            --indicator-active: #f1c40f;
            --tick-track: #1b0c03;
            --panel-bg: rgba(255, 255, 255, 0.08);
        }

        /* Piano Theme Overrides */
        body.piano-mode {
            --bg-color: #0d0d0d;
            --wood-body: linear-gradient(180deg, #2b2b2b, #111111);
            --wood-border: #444444;
            --accent-gold: #d4af37;
            --indicator-active: #00d2ff;
            --tick-track: #050505;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--bg-color);
            color: #f5e6d3;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            transition: background-color 0.4s ease;
        }

        .container {
            background: rgba(0, 0, 0, 0.4);
            padding: 20px;
            border-radius: 20px;
            width: 320px;
            text-align: center;
            box-shadow: 0 15px 35px rgba(0,0,0,0.8);
            border: 1px solid rgba(255, 255, 255, 0.08);
        }

        /* Title Bar */
        .title-bar {
            margin-bottom: 12px;
        }

        h1 {
            font-family: 'Georgia', serif;
            font-size: 0.95rem;
            letter-spacing: 3px;
            color: var(--accent-gold);
            text-transform: uppercase;
            margin: 0;
            opacity: 0.8;
        }

        /* Individual Settings Buttons Bar */
        .options-nav {
            display: flex;
            justify-content: space-between;
            gap: 6px;
            margin-bottom: 10px;
        }

        .opt-btn {
            flex: 1;
            background: rgba(255, 255, 255, 0.08);
            border: 1px solid rgba(255, 255, 255, 0.15);
            color: #d1b89d;
            padding: 6px 4px;
            border-radius: 10px;
            font-size: 0.68rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.2s;
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 2px;
        }

        .opt-btn .btn-val {
            color: var(--accent-gold);
            font-weight: bold;
            font-size: 0.72rem;
        }

        .opt-btn:hover, .opt-btn.active {
            background: rgba(255, 255, 255, 0.18);
            color: #fff;
            border-color: var(--accent-gold);
        }

        /* Sub-menus for Options */
        .sub-menu {
            display: none;
            background: var(--panel-bg);
            border: 1px solid rgba(255, 255, 255, 0.12);
            border-radius: 10px;
            padding: 10px;
            margin-bottom: 12px;
            font-size: 0.8rem;
            animation: fadeIn 0.2s ease-in-out;
        }

        .sub-menu.open {
            display: block;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-4px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .sub-menu-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .sub-menu-content label {
            font-weight: 600;
            color: #d1b89d;
            font-size: 0.75rem;
        }

        select, input[type="number"] {
            background: #1a1a1a;
            color: var(--accent-gold);
            border: 1px solid rgba(255, 255, 255, 0.2);
            padding: 4px 8px;
            border-radius: 6px;
            font-size: 0.8rem;
            outline: none;
            text-align: center;
        }

        input[type="number"] {
            width: 60px;
        }

        /* Compact Switchers Row (Instrument & Visual Layout) */
        .switchers-row {
            display: flex;
            justify-content: space-between;
            gap: 6px;
            margin-bottom: 15px;
        }

        .mode-toggle {
            display: flex;
            gap: 4px;
            flex: 1;
        }

        .mode-btn {
            flex: 1;
            background: transparent;
            border: 1px solid rgba(255, 255, 255, 0.2);
            color: #aaa;
            padding: 3px 6px;
            border-radius: 12px;
            font-size: 0.6rem;
            cursor: pointer;
            transition: all 0.2s;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .mode-btn.active {
            background: var(--accent-gold);
            border-color: var(--accent-gold);
            color: #000;
            font-weight: bold;
        }

        /* Metronome Visual Display Box */
        .visual-display-box {
            width: 100%;
            height: 190px;
            margin: 0 auto 15px auto;
            background: var(--panel-bg);
            border-radius: 12px;
            position: relative;
            box-sizing: border-box;
            border: 1px solid rgba(255, 255, 255, 0.08);
            display: flex;
            justify-content: center;
            align-items: center;
            overflow: hidden;
        }

        /* Pyramid Metronome Body */
        .metronome-pyramid {
            width: 170px;
            height: 190px;
            background: var(--wood-body);
            clip-path: polygon(30% 0%, 70% 0%, 100% 100%, 0% 100%);
            position: relative;
            box-shadow: inset 0 0 20px rgba(0,0,0,0.8);
            border-bottom: 5px solid var(--wood-border);
        }

        /* Inner Pendulum Chamber */
        .pendulum-chamber {
            position: absolute;
            top: 15px;
            left: 50%;
            transform: translateX(-50%);
            width: 65px;
            height: 160px;
            background: var(--tick-track);
            clip-path: polygon(35% 0%, 65% 0%, 100% 100%, 0% 100%);
            overflow: hidden;
            border: 1px solid rgba(255,255,255,0.05);
        }

        .pendulum-pivot {
            position: absolute;
            top: 145px;
            left: 50%;
            width: 0;
            height: 0;
        }

        .pendulum-arm {
            position: absolute;
            bottom: 0;
            left: -2px;
            width: 4px;
            height: 130px;
            background: linear-gradient(to right, #e0e0e0, #999);
            transform-origin: bottom center;
            transform: rotate(0deg);
            transition: transform 0.08s ease-out;
        }

        .pendulum-weight {
            width: 16px;
            height: 20px;
            background: linear-gradient(135deg, #f39c12, #f1c40f, #9a7d0a);
            border-radius: 3px;
            position: absolute;
            top: 35px;
            left: -6px;
            border: 1px solid #7d6608;
        }

        /* Minimalist Light-Up Dots Mode View */
        .minimal-view {
            display: none;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            width: 100%;
            height: 100%;
            gap: 16px;
        }

        .visual-display-box.minimal-active .metronome-pyramid {
            display: none;
        }

        .visual-display-box.minimal-active .minimal-view {
            display: flex;
        }

        .display-dots-container {
            display: flex;
            justify-content: center;
            gap: 12px;
        }

        .display-dot {
            width: 18px;
            height: 18px;
            background-color: rgba(255, 255, 255, 0.12);
            border-radius: 50%;
            border: 1px solid rgba(255, 255, 255, 0.2);
            transition: all 0.08s ease;
        }

        .display-dot.active {
            background-color: var(--indicator-active);
            border-color: #fff;
            transform: scale(1.3);
            box-shadow: 0 0 16px var(--indicator-active);
        }

        .flash-beat-text {
            font-size: 0.85rem;
            color: var(--accent-gold);
            font-weight: bold;
            letter-spacing: 2px;
        }

        /* BPM Display */
        .bpm-display {
            font-size: 2.8rem;
            font-weight: bold;
            color: #fff;
            line-height: 1;
        }

        .bpm-label {
            font-size: 0.75rem;
            color: var(--accent-gold);
            letter-spacing: 2px;
            margin-bottom: 10px;
        }

        /* Controls */
        input[type=range] {
            width: 100%;
            accent-color: var(--accent-gold);
            cursor: pointer;
            margin-bottom: 12px;
        }

        .btn-group {
            display: flex;
            justify-content: center;
            gap: 6px;
            margin-bottom: 15px;
        }

        button.ctrl-btn {
            background-color: rgba(255,255,255,0.08);
            color: #fff;
            border: 1px solid rgba(255,255,255,0.15);
            padding: 6px 12px;
            border-radius: 6px;
            font-size: 0.8rem;
            font-weight: bold;
            cursor: pointer;
        }

        button.ctrl-btn:hover {
            background-color: rgba(255,255,255,0.2);
        }

        /* Enhanced Play/Stop Button with Geometric Icons */
        .play-btn {
            background: linear-gradient(135deg, #2ecc71, #27ae60);
            width: 100%;
            padding: 14px;
            border: none;
            color: white;
            border-radius: 12px;
            cursor: pointer;
            box-shadow: 0 6px 20px rgba(39, 174, 96, 0.4);
            display: flex;
            justify-content: center;
            align-items: center;
            transition: all 0.25s cubic-bezier(0.4, 0, 0.2, 1);
        }

        .play-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(39, 174, 96, 0.6);
        }

        .play-btn:active {
            transform: translateY(1px);
        }

        .play-btn.playing {
            background: linear-gradient(135deg, #e74c3c, #c0392b);
            box-shadow: 0 6px 20px rgba(192, 57, 43, 0.4);
        }

        .play-btn.playing:hover {
            box-shadow: 0 8px 25px rgba(192, 57, 43, 0.6);
        }

        /* Pure CSS Geometric Shapes */
        .icon-play {
            width: 0;
            height: 0;
            border-top: 10px solid transparent;
            border-bottom: 10px solid transparent;
            border-left: 17px solid white;
            margin-left: 4px; /* optical centering */
            filter: drop-shadow(0 2px 4px rgba(0,0,0,0.2));
            transition: transform 0.2s ease;
        }

        .icon-stop {
            width: 16px;
            height: 16px;
            background-color: white;
            border-radius: 3px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.2);
            transition: transform 0.2s ease;
        }

        .play-btn:hover .icon-play,
        .play-btn:hover .icon-stop {
            transform: scale(1.1);
        }

        /* Beat Dots */
        .dots {
            display: flex;
            justify-content: center;
            gap: 6px;
            margin-bottom: 12px;
        }

        .dot {
            width: 8px;
            height: 8px;
            background-color: rgba(255, 255, 255, 0.15);
            border-radius: 50%;
            transition: background-color 0.1s ease, box-shadow 0.1s ease;
        }

        .dot.active {
            background-color: var(--indicator-active);
            box-shadow: 0 0 8px var(--indicator-active);
        }
    </style>
</head>
<body>

<div class="container">
    <!-- Title -->
    <div class="title-bar">
        <h1>Violanome</h1>
    </div>

    <!-- Private Option Buttons with Active Summaries -->
    <div class="options-nav">
        <button class="opt-btn" id="btnBeatsBar">
            <span>Beats / Bar</span>
            <span class="btn-val" id="valBeatsBar">4 ▾</span>
        </button>
        <button class="opt-btn" id="btnBeatsClick">
            <span>Beats / Click</span>
            <span class="btn-val" id="valBeatsClick">1 ▾</span>
        </button>
        <button class="opt-btn" id="btnBpm">
            <span>Target BPM</span>
            <span class="btn-val" id="valBpm">120 ▾</span>
        </button>
    </div>

    <!-- Private Menu 1: Beats per Bar -->
    <div class="sub-menu" id="menuBeatsBar">
        <div class="sub-menu-content">
            <label for="timeSig">Beats per Bar:</label>
            <select id="timeSig">
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4" selected>4</option>
                <option value="5">5</option>
                <option value="6">6</option>
            </select>
        </div>
    </div>

    <!-- Private Menu 2: Beats per Click -->
    <div class="sub-menu" id="menuBeatsClick">
        <div class="sub-menu-content">
            <label for="subdivision">Subdivision:</label>
            <select id="subdivision">
                <option value="1">1 (Quarter)</option>
                <option value="2">2 (Eighths)</option>
                <option value="3">3 (Triplets)</option>
                <option value="4">4 (16ths)</option>
            </select>
        </div>
    </div>

    <!-- Private Menu 3: Target BPM -->
    <div class="sub-menu" id="menuBpm">
        <div class="sub-menu-content">
            <label for="bpmInput">Exact BPM:</label>
            <input type="number" id="bpmInput" min="40" max="220" value="120">
        </div>
    </div>

    <!-- Switchers Row (Instrument and Display Mode) -->
    <div class="switchers-row">
        <div class="mode-toggle">
            <button class="mode-btn active" id="violinModeBtn">Violin</button>
            <button class="mode-btn" id="pianoModeBtn">Piano</button>
        </div>
        <div class="mode-toggle">
            <button class="mode-btn active" id="pyramidViewBtn">Pyramid</button>
            <button class="mode-btn" id="minimalViewBtn">Dots</button>
        </div>
    </div>

    <!-- Metronome Visual Display Area -->
    <div class="visual-display-box" id="visualDisplayBox">
        <!-- Pyramid Metronome -->
        <div class="metronome-pyramid">
            <div class="pendulum-chamber">
                <div class="pendulum-pivot">
                    <div class="pendulum-arm" id="pendulum">
                        <div class="pendulum-weight" id="pWeight"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Minimalist Light-Up Dots Mode -->
        <div class="minimal-view">
            <div class="display-dots-container" id="displayDotsContainer"></div>
            <div class="flash-beat-text" id="flashBeatText">BEAT 1</div>
        </div>
    </div>

    <!-- Beat Indicator Dots -->
    <div class="dots" id="dotContainer"></div>

    <!-- BPM Display -->
    <div class="bpm-display" id="bpmValue">120</div>
    <div class="bpm-label">BPM</div>

    <!-- Controls -->
    <input type="range" id="bpmSlider" min="40" max="220" value="120">

    <div class="btn-group">
        <button class="ctrl-btn" id="minusFive">-5</button>
        <button class="ctrl-btn" id="minusOne">-1</button>
        <button class="ctrl-btn" id="plusOne">+1</button>
        <button class="ctrl-btn" id="plusFive">+5</button>
    </div>

    <!-- Play/Stop Button with Geometric Shapes -->
    <button class="play-btn" id="playBtn">
        <div class="icon-play" id="playIcon"></div>
    </button>
</div>

<script>
    // State
    let bpm = 120;
    let beatsPerMeasure = 4;
    let clicksPerBeat = 1;
    let currentBeat = 0;
    let currentSubBeat = 0;
    let isPlaying = false;
    let soundMode = 'violin';
    let visualMode = 'pyramid';

    let audioCtx = null;
    let nextNoteTime = 0.0;
    let timerWorkerId = null;
    const lookahead = 25.0; 
    const scheduleAheadTime = 0.1; 

    let swingRight = true;

    // Elements
    const btnBeatsBar = document.getElementById('btnBeatsBar');
    const btnBeatsClick = document.getElementById('btnBeatsClick');
    const btnBpm = document.getElementById('btnBpm');

    const valBeatsBar = document.getElementById('valBeatsBar');
    const valBeatsClick = document.getElementById('valBeatsClick');
    const valBpm = document.getElementById('valBpm');

    const menuBeatsBar = document.getElementById('menuBeatsBar');
    const menuBeatsClick = document.getElementById('menuBeatsClick');
    const menuBpm = document.getElementById('menuBpm');

    const bpmDisplay = document.getElementById('bpmValue');
    const bpmSlider = document.getElementById('bpmSlider');
    const bpmInput = document.getElementById('bpmInput');
    const playBtn = document.getElementById('playBtn');
    const playIcon = document.getElementById('playIcon');
    const dotContainer = document.getElementById('dotContainer');
    const displayDotsContainer = document.getElementById('displayDotsContainer');
    const timeSigSelect = document.getElementById('timeSig');
    const subdivisionSelect = document.getElementById('subdivision');
    const pendulum = document.getElementById('pendulum');
    const pWeight = document.getElementById('pWeight');
    const violinBtn = document.getElementById('violinModeBtn');
    const pianoBtn = document.getElementById('pianoModeBtn');
    
    const pyramidViewBtn = document.getElementById('pyramidViewBtn');
    const minimalViewBtn = document.getElementById('minimalViewBtn');
    const visualDisplayBox = document.getElementById('visualDisplayBox');
    const flashBeatText = document.getElementById('flashBeatText');

    // Private Menu Toggle Helper
    function togglePrivateMenu(selectedBtn, selectedMenu) {
        const isOpen = selectedMenu.classList.contains('open');

        [menuBeatsBar, menuBeatsClick, menuBpm].forEach(m => m.classList.remove('open'));
        [btnBeatsBar, btnBeatsClick, btnBpm].forEach(b => b.classList.remove('active'));

        if (!isOpen) {
            selectedMenu.classList.add('open');
            selectedBtn.classList.add('active');
        }
    }

    btnBeatsBar.addEventListener('click', () => togglePrivateMenu(btnBeatsBar, menuBeatsBar));
    btnBeatsClick.addEventListener('click', () => togglePrivateMenu(btnBeatsClick, menuBeatsClick));
    btnBpm.addEventListener('click', () => togglePrivateMenu(btnBpm, menuBpm));

    function updateWeightPosition() {
        const percent = (bpm - 40) / (220 - 40); 
        const topPos = 10 + (percent * 70); 
        pWeight.style.top = `${topPos}px`;
    }

    function renderDots() {
        // Bottom dots
        dotContainer.innerHTML = '';
        for (let i = 0; i < beatsPerMeasure; i++) {
            const dot = document.createElement('div');
            dot.classList.add('dot');
            if (i === 0 && isPlaying) dot.classList.add('active');
            dotContainer.appendChild(dot);
        }

        // Central display box dots matching beats per bar
        displayDotsContainer.innerHTML = '';
        for (let i = 0; i < beatsPerMeasure; i++) {
            const dot = document.createElement('div');
            dot.classList.add('display-dot');
            if (i === 0 && isPlaying) dot.classList.add('active');
            displayDotsContainer.appendChild(dot);
        }
    }

    // Soft, Acoustic Wood Click Sound Generator
    function playClick(isAccent, isSubdivision, time) {
        const osc = audioCtx.createOscillator();
        const gain = audioCtx.createGain();
        const filter = audioCtx.createBiquadFilter();

        filter.type = 'lowpass';
        filter.frequency.setValueAtTime(soundMode === 'violin' ? 1200 : 800, time);

        if (soundMode === 'violin') {
            osc.type = 'triangle';
            let startFreq = isAccent ? 600 : 420;
            if (isSubdivision) startFreq = 300;

            osc.frequency.setValueAtTime(startFreq, time);
            osc.frequency.exponentialRampToValueAtTime(100, time + 0.03);

            gain.gain.setValueAtTime(isSubdivision ? 0.3 : 0.7, time);
            gain.gain.exponentialRampToValueAtTime(0.001, time + 0.04);
        } else {
            osc.type = 'sine';
            let startFreq = isAccent ? 350 : 220;
            if (isSubdivision) startFreq = 160;

            osc.frequency.setValueAtTime(startFreq, time);
            osc.frequency.exponentialRampToValueAtTime(60, time + 0.04);

            gain.gain.setValueAtTime(isSubdivision ? 0.35 : 0.85, time);
            gain.gain.exponentialRampToValueAtTime(0.001, time + 0.05);
        }

        osc.connect(filter);
        filter.connect(gain);
        gain.connect(audioCtx.destination);

        osc.start(time);
        osc.stop(time + 0.06);
    }

    function nextNote() {
        const secondsPerBeat = 60.0 / bpm;
        nextNoteTime += secondsPerBeat / clicksPerBeat;

        currentSubBeat++;
        if (currentSubBeat >= clicksPerBeat) {
            currentSubBeat = 0;
            currentBeat = (currentBeat + 1) % beatsPerMeasure;
        }
    }

    function scheduleNote(beatNumber, subBeatNumber, time) {
        const isMainBeat = (subBeatNumber === 0);
        const isAccent = isMainBeat && (beatNumber === 0);
        
        playClick(isAccent, !isMainBeat, time);

        const delay = Math.max(0, (time - audioCtx.currentTime) * 1000);
        setTimeout(() => {
            if (!isPlaying) return;
            
            // Update bottom indicator dots
            const dots = document.querySelectorAll('.dot');
            dots.forEach((dot, idx) => {
                dot.classList.toggle('active', idx === beatNumber);
            });

            // Update central display dots (light up specific dot on main beat)
            const displayDots = document.querySelectorAll('.display-dot');
            displayDots.forEach((dot, idx) => {
                dot.classList.toggle('active', isMainBeat && idx === beatNumber);
            });

            if (isMainBeat) {
                // Pyramid animation update
                const angle = swingRight ? 20 : -20;
                pendulum.style.transform = `rotate(${angle}deg)`;
                swingRight = !swingRight;

                // Pulse mode text update
                flashBeatText.textContent = `BEAT ${beatNumber + 1}`;
            } else {
                flashBeatText.textContent = `SUB ${subBeatNumber + 1}`;
            }

        }, delay);
    }

    function scheduler() {
        while (nextNoteTime < audioCtx.currentTime + scheduleAheadTime) {
            scheduleNote(currentBeat, currentSubBeat, nextNoteTime);
            nextNote();
        }
    }

    function toggleMetronome() {
        if (!audioCtx) {
            audioCtx = new (window.AudioContext || window.webkitAudioContext)();
        } else if (audioCtx.state === 'suspended') {
            audioCtx.resume();
        }

        isPlaying = !isPlaying;

        if (isPlaying) {
            playBtn.classList.add('playing');
            playIcon.className = 'icon-stop';
            currentBeat = 0;
            currentSubBeat = 0;
            swingRight = true;
            nextNoteTime = audioCtx.currentTime + 0.05;
            timerWorkerId = setInterval(scheduler, lookahead);
        } else {
            playBtn.classList.remove('playing');
            playIcon.className = 'icon-play';
            clearInterval(timerWorkerId);
            pendulum.style.transform = 'rotate(0deg)';
            flashBeatText.textContent = 'BEAT 1';
            renderDots();
        }
    }

    function setBpm(newBpm) {
        bpm = Math.min(Math.max(newBpm, 40), 220);
        bpmDisplay.textContent = bpm;
        bpmSlider.value = bpm;
        bpmInput.value = bpm;
        valBpm.textContent = `${bpm} ▾`;
        updateWeightPosition();
    }

    // Instrument Mode Handlers
    violinBtn.addEventListener('click', () => {
        soundMode = 'violin';
        document.body.classList.remove('piano-mode');
        violinBtn.classList.add('active');
        pianoBtn.classList.remove('active');
    });

    pianoBtn.addEventListener('click', () => {
        soundMode = 'piano';
        document.body.classList.add('piano-mode');
        pianoBtn.classList.add('active');
        violinBtn.classList.remove('active');
    });

    // Visual Display Mode Handlers
    pyramidViewBtn.addEventListener('click', () => {
        visualMode = 'pyramid';
        visualDisplayBox.classList.remove('minimal-active');
        pyramidViewBtn.classList.add('active');
        minimalViewBtn.classList.remove('active');
    });

    minimalViewBtn.addEventListener('click', () => {
        visualMode = 'minimal';
        visualDisplayBox.classList.add('minimal-active');
        minimalViewBtn.classList.add('active');
        pyramidViewBtn.classList.remove('active');
    });

    // Listeners
    playBtn.addEventListener('click', toggleMetronome);
    bpmSlider.addEventListener('input', (e) => setBpm(parseInt(e.target.value)));
    bpmInput.addEventListener('change', (e) => setBpm(parseInt(e.target.value)));

    document.getElementById('minusFive').addEventListener('click', () => setBpm(bpm - 5));
    document.getElementById('minusOne').addEventListener('click', () => setBpm(bpm - 1));
    document.getElementById('plusOne').addEventListener('click', () => setBpm(bpm + 1));
    document.getElementById('plusFive').addEventListener('click', () => setBpm(bpm + 5));

    timeSigSelect.addEventListener('change', (e) => {
        beatsPerMeasure = parseInt(e.target.value);
        valBeatsBar.textContent = `${beatsPerMeasure} ▾`;
        currentBeat = 0;
        currentSubBeat = 0;
        renderDots();
    });

    subdivisionSelect.addEventListener('change', (e) => {
        clicksPerBeat = parseInt(e.target.value);
        valBeatsClick.textContent = `${clicksPerBeat} ▾`;
        currentSubBeat = 0;
    });

    // Init
    renderDots();
    updateWeightPosition();
</script>

</body>
</html>
