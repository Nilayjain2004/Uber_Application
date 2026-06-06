/* ═══════════════════════════════════════════
   RIDEX — app.js
   Spring Boot backend: http://localhost:8080
═══════════════════════════════════════════ */

const BASE = window.location.protocol === 'file:' ? 'http://localhost:8081' : window.location.origin;

/* ── State ── */
let accessToken = null;
let currentRole  = null;   // 'RIDER' | 'DRIVER' | 'ADMIN'
let riderPage    = 0;
let driverPage   = 0;
let driverRequestsPage = 0;

/* ══════════════════════════════════════════
   UTILS
══════════════════════════════════════════ */

function showToast(msg, type = '') {
  const t = document.getElementById('toast');
  t.textContent = msg;
  t.className   = 'toast show ' + type;
  clearTimeout(t._timer);
  t._timer = setTimeout(() => { t.className = 'toast'; }, 3500);
}

function showScreen(id) {
  document.querySelectorAll('.screen').forEach(s => s.classList.remove('active'));
  document.getElementById(id).classList.add('active');
}

function setStatus(id, msg, color = '') {
  const el = document.getElementById(id);
  if (!el) return;
  el.textContent = msg;
  el.style.color  = color || 'var(--accent2)';
}
async function api(method, path, body, auth = true) {
  const getToken = () => localStorage.getItem("accessToken");

  let res = await fetch(BASE + path, {
    method,
    headers: {
      "Content-Type": "application/json",
      ...(auth && getToken()
        ? { "Authorization": `Bearer ${getToken()}` }
        : {})
    },
    credentials: "include",
    body: body ? JSON.stringify(body) : null
  });

  // 🔥 AUTO REFRESH ON 401
  if (res.status === 401 && auth) {
    console.log("Token expired → refreshing...");

    const refreshRes = await fetch(BASE + "/auth/refresh", {
      method: "POST",
      credentials: "include"
    });

    if (!refreshRes.ok) {
      logout();
      throw new Error("Session expired");
    }

    const refreshData = await refreshRes.json();
    const newToken = refreshData?.data?.accessToken;

    if (!newToken) {
      logout();
      throw new Error("Refresh failed");
    }

    localStorage.setItem("accessToken", newToken);

    // 🔁 retry with fresh token
    res = await fetch(BASE + path, {
      method,
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${newToken}`
      },
      credentials: "include",
      body: body ? JSON.stringify(body) : null
    });
  }

  const data = await readResponse(res);

  if (!res.ok) {
    throw new Error(getErrorMessage(data, res.status));
  }

  return unwrapApiResponse(data);
}

async function readResponse(res) {
  if (!res.headers.get("content-type")?.includes("application/json")) {
    return res.text();
  }

  return res.json();
}

function unwrapApiResponse(response) {
  if (response && typeof response === 'object' && Object.prototype.hasOwnProperty.call(response, 'data')) {
    return response.data;
  }

  return response;
}

function getErrorMessage(response, status) {
  if (response?.error?.message) return response.error.message;
  if (response?.message) return response.message;
  if (response?.error) return response.error;
  return `Error ${status}`;
}
/* ══════════════════════════════════════════
   AUTH TABS
══════════════════════════════════════════ */
document.querySelectorAll('.tab').forEach(tab => {
  tab.addEventListener('click', () => {
    const name = tab.dataset.tab;
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.tab-panel').forEach(p => p.classList.remove('active'));
    tab.classList.add('active');
    document.getElementById('tab-' + name).classList.add('active');
  });
});

/* ══════════════════════════════════════════
   SIDEBAR NAV
══════════════════════════════════════════ */
function initSidebar(screenId, panelPrefix) {
  const screen = document.getElementById(screenId);
  screen.querySelectorAll('.side-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      screen.querySelectorAll('.side-btn').forEach(b => b.classList.remove('active'));
      screen.querySelectorAll('.panel').forEach(p => p.classList.remove('active'));
      btn.classList.add('active');
      const panel = document.getElementById('panel-' + btn.dataset.panel);
      if (panel) panel.classList.add('active');
    });
  });
}

function activatePanel(screenId, panelName) {
  const screen = document.getElementById(screenId);
  if (!screen) return;

  screen.querySelectorAll('.side-btn').forEach(btn => {
    btn.classList.toggle('active', btn.dataset.panel === panelName);
  });
  screen.querySelectorAll('.panel').forEach(panel => {
    panel.classList.toggle('active', panel.id === 'panel-' + panelName);
  });
}

function showDashboardForRoles(roles) {
  if (roles.includes("ADMIN")) {
    showScreen('admin-screen');
  } else if (roles.includes("DRIVER")) {
    showScreen('driver-screen');
    activatePanel('driver-screen', 'd-requests');
    loadDriverRideRequests();
  } else if (roles.includes("RIDER")) {
    showScreen('rider-screen');
  }
}
/* ══════════════════════════════════════════
   SIGNUP
══════════════════════════════════════════ */

async function handleSignup() {
  const fname = document.getElementById('signup-fname').value.trim();
  const lname = document.getElementById('signup-lname').value.trim();
  const email = document.getElementById('signup-email').value.trim();
  const password = document.getElementById('signup-password').value;

  if (!fname || !lname || !email || !password) {
    document.getElementById("signup-error").innerText = "All fields are required";
    return;
  }

  const data = {
    name: fname + " " + lname,
    email: email,
    password: password
  };

  try {
    const response = await fetch(BASE + "/auth/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      credentials: "include",
      body: JSON.stringify(data)
    });
    const result = await readResponse(response);

    if (!response.ok) {
      throw new Error(getErrorMessage(result, response.status));
    }

    alert("Account created successfully!");

  } catch (err) {
    document.getElementById("signup-error").innerText = err.message;
  }
}
/* ══════════════════════════════════════════
   LOGIN
══════════════════════════════════════════ */


async function handleDriverSignup() {
  const fname = document.getElementById('driver-signup-fname').value.trim();
  const lname = document.getElementById('driver-signup-lname').value.trim();
  const email = document.getElementById('driver-signup-email').value.trim();
  const password = document.getElementById('driver-signup-password').value;
  const vehicleId = document.getElementById('driver-signup-vehicle').value.trim();
  const currentLatitude = parseFloat(document.getElementById('driver-signup-lat').value);
  const currentLongitude = parseFloat(document.getElementById('driver-signup-lng').value);
  const error = document.getElementById("driver-signup-error");

  error.innerText = "";

  if (!fname || !lname || !email || !password || !vehicleId || Number.isNaN(currentLatitude) || Number.isNaN(currentLongitude)) {
    error.innerText = "All fields are required";
    return;
  }

  try {
    await api('POST', '/auth/signup/driver', {
      name: fname + " " + lname,
      email,
      password,
      vehicleId,
      currentLatitude,
      currentLongitude
    }, false);

    showToast("Driver account created. Please login.", "success");
    document.querySelector('.tab[data-tab="login"]').click();
  } catch (err) {
    error.innerText = err.message;
  }
}

async function handleLogin() {
  console.log("Login clicked");

  const email = document.getElementById('login-email').value;
  const password = document.getElementById('login-password').value;

  try {
    const response = await fetch(BASE + "/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      credentials: "include",
      body: JSON.stringify({ email, password })
    });
    const result = await readResponse(response);

    if (!response.ok) {
      throw new Error(getErrorMessage(result, response.status));
    }

    const data = { data: unwrapApiResponse(result) };

    accessToken = data.data.accessToken; // ✅ global
    localStorage.setItem("accessToken", accessToken); // ✅ store token

    const payload = JSON.parse(atob(accessToken.split('.')[1]));
    let roles = payload.roles || [];

    // temporary fix if backend still sends string
    if (typeof roles === "string") {
      roles = roles.replace("[", "").replace("]", "").split(",");
    }

    console.log("Roles:", roles);

    showDashboardForRoles(roles);

  } catch (error) {
    console.error("ERROR:", error);
  }
}
/* ══════════════════════════════════════════
   LOGOUT
══════════════════════════════════════════ */
function logout() {
  accessToken = null;
  currentRole = null;

  // reset app state
  riderPage = 0;
  driverPage = 0;

  // remove stored token
  localStorage.removeItem("accessToken");

  // go to login screen
  showScreen('auth-screen');

  showToast('Logged out successfully.');
}

/* ══════════════════════════════════════════
   RIDER ACTIONS
══════════════════════════════════════════ */
//async function requestRide() {
//  const pickupLat  = parseFloat(document.getElementById('pickup-lat').value);
//  const pickupLng  = parseFloat(document.getElementById('pickup-lng').value);
//  const dropoffLat = parseFloat(document.getElementById('dropoff-lat').value);
//  const dropoffLng = parseFloat(document.getElementById('dropoff-lng').value);
//
//  if ([pickupLat, pickupLng, dropoffLat, dropoffLng].some(isNaN)) {
//    setStatus('ride-request-status', 'Please enter valid coordinates.', 'var(--danger)');
//    return;
//  }
//
//  const body = {
//    pickupLocation: {
//      latitude: pickupLat,
//      longitude: pickupLng
//    },
//    dropOffLocation: {
//      latitude: dropoffLat,
//      longitude: dropoffLng
//    },
//    paymentMethod: "CASH"
//  };
//
//  setStatus('ride-request-status', '<span class="spinner"></span> Finding you a driver…');
//
//  try {
//    const res = await api('POST', '/rider/requestRide', body);
//    const data = res.data || res;
//
//    setStatus(
//      'ride-request-status',
//      `✅ Ride #${data.id} requested! Status: ${data.rideRequestStatus || 'PENDING'}`
//    );
//
//    showToast('Ride requested! Drivers have been notified.', 'success');
//
//  } catch (e) {
//    setStatus('ride-request-status', '❌ ' + e.message, 'var(--danger)');
//  }
//}

async function requestRide() {
  const pickupLat  = parseFloat(document.getElementById('pickup-lat').value);
  const pickupLng  = parseFloat(document.getElementById('pickup-lng').value);
  const dropoffLat = parseFloat(document.getElementById('dropoff-lat').value);
  const dropoffLng = parseFloat(document.getElementById('dropoff-lng').value);

  if ([pickupLat, pickupLng, dropoffLat, dropoffLng].some(isNaN)) {
    setStatus('ride-request-status', 'Please enter valid coordinates.', 'var(--danger)');
    return;
  }

  const body = {
    pickupLocation: {
      coordinates: [pickupLng, pickupLat]   // 👈 FIX HERE
    },
    dropOffLocation: {
      coordinates: [dropoffLng, dropoffLat] // 👈 FIX HERE
    },
    paymentMethod: "CASH"
  };

  setStatus('ride-request-status', '<span class="spinner"></span> Finding you a driver…');

  try {
    const data = await api('POST', '/rider/requestRide', body);

    setStatus(
      'ride-request-status',
      `✅ Ride #${data.id} requested! Status: ${data.rideRequestStatus || 'PENDING'}`
    );

    showToast('Ride requested! Drivers have been notified.', 'success');

  } catch (e) {
    setStatus('ride-request-status', '❌ ' + e.message, 'var(--danger)');
  }
}
/* ── Rider: Cancel Ride ── */
async function cancelRideRider(rideId) {
  try {
    await api('POST', `/rider/cancelRide/${rideId}`, null);
    showToast('Ride cancelled.', '');
    loadRiderRides();
  } catch (e) {
    showToast('Cancel failed: ' + e.message, 'error');
  }
}

/* ── Rider: My Rides ── */
async function loadRiderRides() {
  const list = document.getElementById('rider-rides-list');
  list.innerHTML = '<div class="empty-state"><span class="spinner"></span> Loading…</div>';

  try {
    const data = await api('GET', `/rider/getMyRides?pageOffset=${riderPage}&pageSize=10`);
    renderRidesList(list, data.content || data, 'rider');
    document.getElementById('rider-page-info').textContent =
      `Page ${riderPage + 1} of ${data.totalPages || '?'}`;
  } catch (e) {
    list.innerHTML = `<div class="empty-state" style="color:var(--danger)">Error: ${e.message}</div>`;
  }
}

/* ── Rider: Profile ── */
async function loadRiderProfile() {
  const card = document.getElementById('rider-profile-card');
  card.innerHTML = '<div class="empty-state"><span class="spinner"></span> Loading…</div>';

  try {
    const d = await api('GET', '/rider/getMyProfile');
    card.innerHTML = buildProfileCard(d, 'rider');
  } catch (e) {
    card.innerHTML = `<div class="empty-state" style="color:var(--danger)">Error: ${e.message}</div>`;
  }
}

function goProfile(role) {
  document.querySelectorAll('.panel').forEach(p => p.classList.remove('active'));
  document.getElementById('panel-profile').classList.add('active');
}
/* ── Rider: Rate Driver ── */
async function rateDriverFromPanel(rideId, rating) {
  try {
    await api('POST', '/rider/rateDriver', { rideId, rating });
    showToast('Driver rated! ⭐', 'success');
  } catch (e) {
    showToast('Rating failed: ' + e.message, 'error');
  }
}

/* ══════════════════════════════════════════
   DRIVER ACTIONS
══════════════════════════════════════════ */
async function acceptRide(rideRequestId = null) {
  const id = rideRequestId || document.getElementById('d-accept-id').value;
  if (!id) { setStatus('accept-status', 'Enter ride request ID', 'var(--danger)'); return; }
  try {
    const d = await api('POST', `/drivers/acceptRide/${id}`, null);
    setStatus('accept-status', `Ride #${d.id} accepted. Ask the rider for OTP to start.`);
    showToast('Ride accepted!', 'success');
    loadDriverRideRequests();
  } catch (e) {
    setStatus('accept-status', '❌ ' + e.message, 'var(--danger)');
  }
}

async function loadDriverRideRequests() {
  const list = document.getElementById('driver-requests-list');
  list.innerHTML = '<div class="empty-state"><span class="spinner"></span> Loading...</div>';

  try {
    const data = await api('GET', `/drivers/rideRequests?pageOffset=${driverRequestsPage}&pageSize=10`);
    renderRideRequestsList(list, data.content || []);
    document.getElementById('driver-requests-page-info').textContent =
      `Page ${driverRequestsPage + 1} of ${data.totalPages || '?'}`;
  } catch (e) {
    list.innerHTML = `<div class="empty-state" style="color:var(--danger)">Error: ${e.message}</div>`;
  }
}

async function startRide() {
  const id  = document.getElementById('d-start-id').value;
  const otp = document.getElementById('d-otp').value;
  if (!id || !otp) { setStatus('start-status', 'Enter both ID and OTP', 'var(--danger)'); return; }
  try {
    const d = await api('POST', `/drivers/startRide/${id}`, { otp });
    setStatus('start-status', `▶️ Ride #${d.id} started!`);
    showToast('Ride started! Drive safe. 🚗', 'success');
  } catch (e) {
    setStatus('start-status', '❌ ' + e.message, 'var(--danger)');
  }
}

async function endRide() {
  const id = document.getElementById('d-end-id').value;
  if (!id) { setStatus('end-status', 'Enter ride ID', 'var(--danger)'); return; }
  try {
    const d = await api('POST', `/drivers/endRide/${id}`, null);
    setStatus('end-status', `🏁 Ride #${d.id} ended! Fare: ₹${d.fare || '—'}`);
    showToast('Ride completed! 🏁', 'success');
  } catch (e) {
    setStatus('end-status', '❌ ' + e.message, 'var(--danger)');
  }
}

async function cancelRideDriver() {
  const id = document.getElementById('d-cancel-id').value;
  if (!id) { setStatus('d-cancel-status', 'Enter ride ID', 'var(--danger)'); return; }
  try {
    await api('POST', `/drivers/cancelRide/${id}`, null);
    setStatus('d-cancel-status', '❌ Ride cancelled.');
    showToast('Ride cancelled.', '');
  } catch (e) {
    setStatus('d-cancel-status', '❌ ' + e.message, 'var(--danger)');
  }
}

async function rateRider() {
  const rideId = document.getElementById('d-rate-ride-id').value;
  const rating = parseInt(document.getElementById('d-rating').value);
  if (!rideId) { setStatus('d-rate-status', 'Enter ride ID', 'var(--danger)'); return; }
  if (!rating) { setStatus('d-rate-status', 'Select a star rating', 'var(--danger)'); return; }
  try {
    await api('POST', '/drivers/rateRider', { rideId, rating });
    setStatus('d-rate-status', `⭐ Rider rated ${rating}/5`);
    showToast('Rider rated!', 'success');
  } catch (e) {
    setStatus('d-rate-status', '❌ ' + e.message, 'var(--danger)');
  }
}

async function loadDriverRides() {
  const list = document.getElementById('driver-rides-list');
  list.innerHTML = '<div class="empty-state"><span class="spinner"></span> Loading…</div>';
  try {
    const data = await api('GET', `/drivers/getMyRides?pageOffset=${driverPage}&pageSize=10`);
    renderRidesList(list, data.content || data, 'driver');
    document.getElementById('driver-page-info').textContent =
      `Page ${driverPage + 1} of ${data.totalPages || '?'}`;
  } catch (e) {
    list.innerHTML = `<div class="empty-state" style="color:var(--danger)">Error: ${e.message}</div>`;
  }
}

async function loadDriverProfile() {
  const card = document.getElementById('driver-profile-card');
  card.innerHTML = '<div class="empty-state"><span class="spinner"></span> Loading…</div>';
  try {
    const d = await api('GET', '/drivers/getMyProfile');
    card.innerHTML = buildProfileCard(d, 'driver');
  } catch (e) {
    card.innerHTML = `<div class="empty-state" style="color:var(--danger)">Error: ${e.message}</div>`;
  }
}

/* ══════════════════════════════════════════
   RENDER HELPERS
══════════════════════════════════════════ */
async function onboardDriver() {
  const userId = document.getElementById('admin-driver-user-id').value;
  const vehicleId = document.getElementById('admin-driver-vehicle').value.trim();
  const currentLatitude = parseFloat(document.getElementById('admin-driver-lat').value);
  const currentLongitude = parseFloat(document.getElementById('admin-driver-lng').value);

  if (!userId || !vehicleId || Number.isNaN(currentLatitude) || Number.isNaN(currentLongitude)) {
    setStatus('admin-onboard-status', 'Enter user ID, vehicle number, latitude, and longitude', 'var(--danger)');
    return;
  }

  try {
    const driver = await api('POST', `/auth/onBoardNewDriver/${userId}`, {
      vehicleId,
      currentLatitude,
      currentLongitude
    });
    const name = driver.user?.name || `User #${userId}`;
    setStatus('admin-onboard-status', `Driver onboarded: ${name} (${driver.vehicleId})`);
    showToast('Driver onboarded successfully.', 'success');
  } catch (e) {
    setStatus('admin-onboard-status', e.message, 'var(--danger)');
  }
}

function renderRideRequestsList(container, requests) {
  if (!requests || requests.length === 0) {
    container.innerHTML = '<div class="empty-state">No pending ride requests.</div>';
    return;
  }

  container.innerHTML = requests.map(request => {
    const from = formatPoint(request.pickupLocation);
    const to = formatPoint(request.dropOffLocation);
    const fare = request.fare ? `Fare: Rs ${request.fare}` : '';

    return `
      <div class="ride-item">
        <div class="ride-icon">ID</div>
        <div class="ride-info">
          <div class="ride-id">Request #${request.id} · ${formatDate(request.requestedTime)}</div>
          <div class="ride-route">${from} -> ${to}</div>
          ${fare ? `<div style="font-size:0.8rem;color:var(--sub);margin-top:2px">${fare}</div>` : ''}
          <button class="btn-sm" onclick="acceptRide(${request.id})" style="margin-top:8px">Accept</button>
        </div>
        <span class="ride-status status-REQUESTED">${request.rideRequestStatus || 'PENDING'}</span>
      </div>`;
  }).join('');
}

function renderRidesList(container, rides, who) {
  if (!rides || rides.length === 0) {
    container.innerHTML = '<div class="empty-state">No rides found.</div>';
    return;
  }

  container.innerHTML = rides.map(ride => {
    const statusClass = 'status-' + (ride.rideStatus || 'UNKNOWN');
    const from = formatPoint(ride.pickupLocation);
    const to = formatPoint(ride.dropOffLocation);

    const riderOtp = who === 'rider' && ride.otp
      ? `<div style="font-size:0.85rem;color:var(--accent);margin-top:4px">OTP: ${ride.otp}</div>`
      : '';
    const extra = who === 'rider'
      ? `${riderOtp}<button class="btn-sm" onclick="cancelRideRider(${ride.id})" style="margin-top:8px">Cancel</button>`
      : '';

    return `
      <div class="ride-item">
        <div class="ride-icon">🚕</div>
        <div class="ride-info">
          <div class="ride-id">Ride #${ride.id} · ${formatDate(ride.createdTime)}</div>
          <div class="ride-route">📍 ${from} → 🏁 ${to}</div>
          ${ride.fare ? `<div style="font-size:0.8rem;color:var(--sub);margin-top:2px">Fare: ₹${ride.fare}</div>` : ''}
          ${extra}
        </div>
        <span class="ride-status ${statusClass}">${ride.rideStatus || 'UNKNOWN'}</span>
      </div>`;
  }).join('');
}

function formatPoint(point) {
  const [lng, lat] = point?.coordinates || [];
  if (typeof lat !== 'number' || typeof lng !== 'number') return 'N/A';
  return `${lat.toFixed(4)}, ${lng.toFixed(4)}`;
}

function buildProfileCard(d, type) {
  const name    = d.user?.name || d.name || 'User';
  const email   = d.user?.email || d.email || '—';
  const rating  = d.rating?.toFixed(1) || '—';
  const rides   = d.totalRides || '—';
  const vehicle = d.vehicle || null;
  const initials = name.split(' ').map(n => n[0]).join('').toUpperCase().slice(0, 2);
  const accentColor = type === 'driver' ? 'var(--driver-c)' : 'var(--rider-c)';

  return `
    <div class="profile-avatar" style="background: linear-gradient(135deg, ${accentColor}, var(--accent2))">${initials}</div>
    <div class="profile-name">${name}</div>
    <div class="profile-email">${email}</div>
    ${vehicle ? `<div style="font-size:0.85rem;color:var(--sub);margin-bottom:12px">🚗 Vehicle: ${vehicle}</div>` : ''}
    <div class="profile-stats">
      <div class="stat-box">
        <div class="stat-val" style="color:${accentColor}">${rating}</div>
        <div class="stat-label">Rating</div>
      </div>
      <div class="stat-box">
        <div class="stat-val">${rides}</div>
        <div class="stat-label">Rides</div>
      </div>
      <div class="stat-box">
        <div class="stat-val" style="color:var(--accent2)">✓</div>
        <div class="stat-label">Verified</div>
      </div>
    </div>`;
}

function formatDate(dateStr) {
  if (!dateStr) return '';
  try {
    return new Date(dateStr).toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: '2-digit' });
  } catch { return dateStr; }
}

/* ══════════════════════════════════════════
   PAGINATION
══════════════════════════════════════════ */
function prevPage(who) {
  if (who === 'rider' && riderPage > 0) { riderPage--; loadRiderRides(); }
  if (who === 'driver' && driverPage > 0) { driverPage--; loadDriverRides(); }
  if (who === 'driverRequests' && driverRequestsPage > 0) { driverRequestsPage--; loadDriverRideRequests(); }
}
function nextPage(who) {
  if (who === 'rider') { riderPage++; loadRiderRides(); }
  if (who === 'driver') { driverPage++; loadDriverRides(); }
  if (who === 'driverRequests') { driverRequestsPage++; loadDriverRideRequests(); }
}

/* ══════════════════════════════════════════
   STAR RATING (DRIVER)
══════════════════════════════════════════ */
document.querySelectorAll('#d-star-row .star').forEach(star => {
  star.addEventListener('click', () => {
    const val = parseInt(star.dataset.val);
    document.getElementById('d-rating').value = val;
    document.querySelectorAll('#d-star-row .star').forEach((s, i) => {
      s.classList.toggle('lit', i < val);
    });
  });
  star.addEventListener('mouseenter', () => {
    const val = parseInt(star.dataset.val);
    document.querySelectorAll('#d-star-row .star').forEach((s, i) => {
      s.classList.toggle('lit', i < val);
    });
  });
});
document.getElementById('d-star-row').addEventListener('mouseleave', () => {
  const val = parseInt(document.getElementById('d-rating').value);
  document.querySelectorAll('#d-star-row .star').forEach((s, i) => {
    s.classList.toggle('lit', i < val);
  });
});

/* ══════════════════════════════════════════
   TOKEN REFRESH (every 9 mins)
══════════════════════════════════════════ */
setInterval(async () => {
  if (!accessToken) return;

  try {
    const response = await fetch(BASE + "/auth/refresh", {
      method: "POST",
      credentials: "include" // 🔥 MUST for cookie
    });

    if (!response.ok) {
      throw new Error("Refresh failed");
    }

    const data = await response.json();

    // ✅ FIX: correct path
    const newToken = data?.data?.accessToken;

    if (newToken) {
      accessToken = newToken;
      localStorage.setItem("accessToken", newToken); // optional
      console.log("[RideX] Token refreshed ✅");
    }

  } catch (err) {
    console.error("Refresh failed:", err);
    showScreen("auth-screen"); // logout user
  }

}, 9 * 60 * 1000); // ⏱️ 9 minutes



/* ══════════════════════════════════════════
   INIT
══════════════════════════════════════════ */
window.addEventListener('DOMContentLoaded', () => {
  initSidebar('rider-screen');
  initSidebar('driver-screen');
  initSidebar('admin-screen');
  showScreen('auth-screen');

  // ✅ restore token after page reload
  const savedToken = localStorage.getItem("accessToken");
  if (savedToken) {
    accessToken = savedToken;
    console.log("Token restored from storage");

    // 🔥 OPTIONAL: auto-redirect user based on role
    const payload = JSON.parse(atob(accessToken.split('.')[1]));
    let roles = payload.roles || [];

    if (typeof roles === "string") {
      roles = roles.replace("[", "").replace("]", "").split(",");
    }

    showDashboardForRoles(roles);
  }
});
