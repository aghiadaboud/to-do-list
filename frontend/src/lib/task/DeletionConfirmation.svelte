<script>
    import Modal from "../common/Modal.svelte";
    import {createEventDispatcher} from "svelte";

    export let showModal = false;
    const dispatch = createEventDispatcher();

    const handleDeleteAllTasks = () => {
        dispatch('deleteAllTasks', {});
        showModal = false;
    }
</script>

<Modal bind:showModal modalWidth="w-fit">

    <div class="flex flex-row">
        <div class="rounded-full border border-gray-300 flex items-center justify-center w-16 h-16 flex-shrink-0 mx-auto">
            <i class="text-3xl">
                &#9888;
            </i>
        </div>
        <div>
            <p class="font-bold px-6">
                Delete Tasks
            </p>

            <p class="px-6 py-3">
              Are you sure, you want to delete all tasks ?
            </p>
        </div>
    </div>

    <div slot="custom-buttons">
        <button
                class="mt-1 md:mt-0 md:ml-2 px-2 py-1 border-2 border-white rounded text-red-600 md:hover:shadow-md md:hover:shadow-white"
                on:click={handleDeleteAllTasks}>Delete
        </button>
    </div>
</Modal>