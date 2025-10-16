function setPermissions() {
  const role = localStorage.getItem("role");

  if (!role) {
    alert("Sessão expirada. Faça login novamente.");
    window.location.href = "login.html";
    return;
  }

  if (role === "Funcionário") {
    document.getElementById("btnEditProduct").disabled = true;
    document.getElementById("btnRegisterProduct").disabled = true;
    document.getElementById("btnSalesReport").disabled = true;
  }
}

document.getElementById("btnRegisterSale").addEventListener("click", () => {
  window.location.href = "register-sale.html";
});

document.getElementById("btnRegisterProduct").addEventListener("click", () => {
  window.location.href = "register-product.html";
});

document.getElementById("btnEditProduct").addEventListener("click", () => {
  window.location.href = "edit-product.html";
});

document.getElementById("btnSalesReport").addEventListener("click", () => {
  window.location.href = "sales-report.html";
});

document.getElementById("btnLeave").addEventListener("click", () => {
  if (confirm("Deseja realmente sair?")) {
    localStorage.clear();
    window.location.href = "login.html";
  }
});

setPermissions();
