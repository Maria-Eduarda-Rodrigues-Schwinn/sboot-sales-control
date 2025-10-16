function showMessage(msg, type = "info") {
  if (type === "error") {
    alert(`Erro: ${msg}`);
  } else {
    alert(msg);
  }
}

const usernameEl = document.getElementById("username");
const passwordEl = document.getElementById("password");
const btnEnter = document.getElementById("btnEnter");
const btnPower = document.getElementById("btnPower");

[usernameEl, passwordEl].forEach((el) =>
  el.addEventListener("keydown", (e) => {
    if (e.key === "Enter") btnEnter.click();
  })
);

btnEnter.addEventListener("click", async () => {
  const login = usernameEl.value.trim();
  const passwordRaw = passwordEl.value;

  if (!login) {
    showMessage("Por favor, preencha o campo de login.");
    usernameEl.focus();
    return;
  }

  if (!passwordRaw) {
    showMessage("Por favor, preencha o campo de senha.");
    passwordEl.focus();
    return;
  }

  try {
    const response = await fetch("/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username: login, password: passwordRaw })
    });

    if (!response.ok) {
      showMessage("Credenciais inválidas. Tente novamente.", "error");
      return;
    }

    const data = await response.json();

    localStorage.setItem("token", data.token);
    localStorage.setItem("role", data.role);

    showMessage(`Olá ${login}, sua permissão é de ${data.role}. Seja bem-vindo!`);

    window.location.href = "main-menu.html";
  } catch (err) {
    console.error("Erro na autenticação:", err);
    showMessage("Erro ao conectar com o servidor.", "error");
  }
});

btnPower.addEventListener("click", () => {
  const wantsExit = confirm("Deseja realmente sair?");
  if (!wantsExit) return;

  localStorage.clear();
  window.location.href = "login.html";
});
