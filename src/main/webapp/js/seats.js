// Seat selection functionality
document.addEventListener('DOMContentLoaded', function() {
    const seatCheckboxes = document.querySelectorAll('input[name="seats"]');
    const selectedSeatsDisplay = document.getElementById('selectedSeats');
    const totalAmountDisplay = document.getElementById('totalAmount');
    
    // Update display when seats are selected
    seatCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', updateBookingSummary);
    });
    
    function updateBookingSummary() {
        const selectedSeats = [];
        let totalAmount = 0;
        
        seatCheckboxes.forEach(checkbox => {
            if (checkbox.checked) {
                selectedSeats.push(checkbox.value);
                totalAmount += parseFloat(checkbox.dataset.price);
            }
        });
        
        // Update selected seats display
        if (selectedSeats.length > 0) {
            selectedSeatsDisplay.textContent = selectedSeats.join(', ');
        } else {
            selectedSeatsDisplay.textContent = 'None';
        }
        
        // Update total amount display
        totalAmountDisplay.textContent = totalAmount.toFixed(2);
    }
});
