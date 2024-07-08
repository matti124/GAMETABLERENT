        function validateForm() {
			console.log("funziono");
            const startDate = document.querySelector('input[name="Start"]').value;
            const endDate = document.querySelector('input[name="end"]').value;

            if (!startDate || !endDate) {
                alert("Entrambe le date devono essere compilate.");
                return false;
            }

            if (new Date(startDate) > new Date(endDate)) {
                alert("La data di inizio non può essere successiva alla data di fine.");
                return false;
            }

            return true;
        }
