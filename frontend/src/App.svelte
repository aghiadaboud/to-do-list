<script>
    import Tasks from "./lib/task/Tasks.svelte";
    import Toasts from "./lib/common/toast-notifications/Toasts.svelte";
</script>

<Toasts />
<main>
    <section>
        <Tasks></Tasks>
    </section>
</main>


<style>
    :global(html) {
        background-color: #302e2b;
    }
</style>
