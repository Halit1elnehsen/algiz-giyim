const API = 'https://algiz-backend.onrender.com';
let cartCount = 0;

// Custom cursor
const cur = document.getElementById('cur');
const curl = document.getElementById('curl');
let mx=0,my=0,lx=0,ly=0;
document.addEventListener('mousemove',e=>{mx=e.clientX;my=e.clientY;cur.style.left=mx+'px';cur.style.top=my+'px';});
(function loop(){lx+=(mx-lx)*.12;ly+=(my-ly)*.12;curl.style.left=lx+'px';curl.style.top=ly+'px';requestAnimationFrame(loop);})();

// Size select
window.selectSize = function(btn){
  document.querySelectorAll('.size-btn').forEach(b=>b.classList.remove('active'));
  btn.classList.add('active');
};

// Tab switch
window.switchTab = function(idx){
  document.querySelectorAll('.tab-btn').forEach((b,i)=>b.classList.toggle('active',i===idx));
  document.querySelectorAll('.tab-pane').forEach((p,i)=>p.classList.toggle('active',i===idx));
};

// Toast
function showToast(msg){
  const t=document.getElementById('toast');
  document.getElementById('toast-msg').textContent=msg;
  t.classList.add('show');
  setTimeout(()=>t.classList.remove('show'),3000);
}

// Cart
window.addToCart = function(){
  const token=localStorage.getItem('token');
  if(!token){openAuthModal();return;}
  cartCount++;
  document.getElementById('cartBadge').textContent='Sepet ('+cartCount+')';
  showToast('Sepete eklendi!');
};

// Auth Modal
window.openAuthModal = function(){
  document.getElementById('authModal').classList.add('show');
};
window.closeAuthModal = function(){
  document.getElementById('authModal').classList.remove('show');
};
window.switchAuthTab = function(idx){
  document.querySelectorAll('.modal-tab').forEach((b,i)=>b.classList.toggle('active',i===idx));
  document.getElementById('loginForm').style.display=idx===0?'flex':'none';
  document.getElementById('registerForm').style.display=idx===1?'flex':'none';
  const ft=document.getElementById('authFooterText');
  ft.innerHTML=idx===0?'Hesabınız yok mu? <a href="#" onclick="switchAuthTab(1);return false;">Kayıt olun</a>':'Zaten hesabınız var mı? <a href="#" onclick="switchAuthTab(0);return false;">Giriş yapın</a>';
};

// Login
window.handleLogin = async function(e){
  e.preventDefault();
  const err=document.getElementById('loginError');
  err.textContent='';
  try{
    const res=await fetch(API+'/api/auth/login',{method:'POST',headers:{'Content-Type':'application/json','Bypass-Tunnel-Reminder':'true'},body:JSON.stringify({email:document.getElementById('loginEmail').value,password:document.getElementById('loginPass').value})});
    if(!res.ok) throw new Error('Geçersiz e-posta veya şifre');
    const data=await res.json();
    localStorage.setItem('token',data.token);
    localStorage.setItem('email',data.email);
    document.getElementById('accountBtn').textContent=data.email.split('@')[0];
    closeAuthModal();
    showToast('Giriş başarılı!');
  }catch(ex){err.textContent=ex.message;}
};

// Register
window.handleRegister = async function(e){
  e.preventDefault();
  const err=document.getElementById('registerError');
  err.textContent='';
  try{
    const res=await fetch(API+'/api/auth/register',{method:'POST',headers:{'Content-Type':'application/json','Bypass-Tunnel-Reminder':'true'},body:JSON.stringify({email:document.getElementById('regEmail').value,password:document.getElementById('regPass').value})});
    if(!res.ok){const d=await res.json().catch(()=>({}));throw new Error(d.message||'Kayıt başarısız');}
    const data=await res.json();
    localStorage.setItem('token',data.token);
    localStorage.setItem('email',data.email);
    document.getElementById('accountBtn').textContent=data.email.split('@')[0];
    closeAuthModal();
    showToast('Kayıt başarılı! Hoşgeldiniz.');
  }catch(ex){err.textContent=ex.message;}
};

// Check existing session
(function(){
  const email=localStorage.getItem('email');
  if(email) document.getElementById('accountBtn').textContent=email.split('@')[0];
})();

// Scroll observer
const observer=new IntersectionObserver(entries=>{
  entries.forEach(e=>{if(e.isIntersecting){e.target.style.opacity='1';e.target.style.transform='translateY(0)';}});
},{threshold:.12});
document.querySelectorAll('.product-hero-card,.lookbook-grid,.newsletter').forEach(el=>{
  el.style.opacity='0';el.style.transform='translateY(32px)';el.style.transition='opacity .8s ease, transform .8s ease';observer.observe(el);
});
