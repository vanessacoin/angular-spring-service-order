const { app, BrowserWindow } = require('electron');
const path = require('path');
const { spawn } = require('child_process');

let win, backend;

function startBackend() {
  const javaBin = app.isPackaged
    ? path.join(process.resourcesPath, 'resources', 'jre', 'bin', 'java.exe')
    : 'java';

  const jarPath = app.isPackaged
    ? path.join(process.resourcesPath, 'resources', 'backend', 'service-order.jar')
    : path.join(__dirname, '..', 'resources', 'backend', 'service-order.jar');

  backend = spawn(javaBin, ['-jar', jarPath], { stdio: 'inherit' });
  backend.on('exit', code => console.log('Spring Boot finalizado:', code));
}

function createWindow() {
  win = new BrowserWindow({
    width: 1280, height: 800,
    webPreferences: { contextIsolation: true }
  });

  const indexPath = app.isPackaged
    ? path.join(process.resourcesPath, 'app', 'index.html')
    : 'http://localhost:4200';

  app.isPackaged ? win.loadFile(indexPath) : win.loadURL(indexPath);

  win.on('closed', () => { win = null; });
}

app.whenReady().then(() => { startBackend(); createWindow(); });
app.on('before-quit', () => { if (backend) backend.kill(); });
app.on('window-all-closed', () => { if (process.platform !== 'darwin') app.quit(); });
app.on('activate', () => { if (win === null) createWindow(); });
